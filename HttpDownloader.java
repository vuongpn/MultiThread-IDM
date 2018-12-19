import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpDownloader extends Downloader{
	
	public HttpDownloader(URL url, String outputFolder, int numConnections) {
		super(url, outputFolder, numConnections);
		download();
	}
	
	private void error() {
		System.out.println("ERROR");
		setState(ERROR);
	}
	
	@Override
	public void run() {
		HttpURLConnection conn = null;
		try {
		
			conn = (HttpURLConnection)mURL.openConnection();
			conn.setConnectTimeout(10000);
			conn.connect();
		
            if (conn.getResponseCode() / 100 != 2) {
                error();
            }
            
            int contentLength = conn.getContentLength();
            if (contentLength < 1) {
                error();
            }
			
            if (mFileSize == -1) {
            	mFileSize = contentLength;
            	stateChanged();
            	System.out.println("Dung luong file: " + mFileSize);
            }
         
            if (mState == DOWNLOADING) {
            	
            	if (mListDownloadThread.size() == 0)
            	{  
            		if (mFileSize > MIN_DOWNLOAD_SIZE) {
		               
						int partSize = Math.round(((float)mFileSize / mNumConnections) / BLOCK_SIZE) * BLOCK_SIZE;
						System.out.println("kich thuoc tung phan: " + partSize);
					
						int startByte = 0;
						int endByte = partSize - 1;
						HttpDownloadThread aThread = new HttpDownloadThread(1, mURL, mOutputFolder + mFileName, startByte, endByte);
						mListDownloadThread.add(aThread);
						int i = 2;
						while (endByte < mFileSize) {
							startByte = endByte + 1;
							endByte += partSize;
							aThread = new HttpDownloadThread(i, mURL, mOutputFolder + mFileName, startByte, endByte);
							mListDownloadThread.add(aThread);
							++i;
						}
            		} else
            		{
            			HttpDownloadThread aThread = new HttpDownloadThread(1, mURL, mOutputFolder + mFileName, 0, mFileSize);
						mListDownloadThread.add(aThread);
            		}
            	} else { 
            		for (int i=0; i<mListDownloadThread.size(); ++i) {
            			if (!mListDownloadThread.get(i).isFinished())
            				mListDownloadThread.get(i).download();
            		}
            	}
				
				for (int i=0; i<mListDownloadThread.size(); ++i) {
					mListDownloadThread.get(i).waitFinish();
				}
				
				if (mState == DOWNLOADING) {
					setState(COMPLETED);
				}
            }
		} catch (Exception e) {
			error();
		} finally {
			if (conn != null)
				conn.disconnect();
		}
	}

	private class HttpDownloadThread extends DownloadThread {
	
		public HttpDownloadThread(int threadID, URL url, String outputFile, int startByte, int endByte) {
			super(threadID, url, outputFile, startByte, endByte);
		}

		@Override
		public void run() {
			BufferedInputStream in = null;
			RandomAccessFile raf = null;
			
			try {
				
				HttpURLConnection conn = (HttpURLConnection)mURL.openConnection();
				
				String byteRange = mStartByte + "-" + mEndByte;
				conn.setRequestProperty("Range", "bytes=" + byteRange);
				System.out.println("bytes=" + byteRange);
			
				conn.connect();
				
	            if (conn.getResponseCode() / 100 != 2) {
	                error();
	            }
				
				in = new BufferedInputStream(conn.getInputStream());
				
				raf = new RandomAccessFile(mOutputFile, "rw");
				raf.seek(mStartByte);
				
				byte data[] = new byte[BUFFER_SIZE];
				int numRead;
				while((mState == DOWNLOADING) && ((numRead = in.read(data,0,BUFFER_SIZE)) != -1))
				{
					raf.write(data,0,numRead);
					
					mStartByte += numRead;
					
					downloaded(numRead);
				}
				
				if (mState == DOWNLOADING) {
					mIsFinished = true;
				}
			} catch (IOException e) {
				error();
			} finally {
				if (raf != null) {
					try {
						raf.close();
					} catch (IOException e) {}
				}
				
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {}
				}
			}
			
			System.out.println("ket thuc thread " + mThreadID);
		}
	}
}