import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;

public abstract class Downloader extends Observable implements Runnable{

	protected URL mURL;

	protected String mOutputFolder;

	protected int mNumConnections;
	
	protected String mFileName;
	
	protected int mFileSize;
	
	protected int mState;
	
	protected int mDownloaded;

	protected ArrayList<DownloadThread> mListDownloadThread;
	
	protected static final int BLOCK_SIZE = 4096;
	protected static final int BUFFER_SIZE = 4096;
	protected static final int MIN_DOWNLOAD_SIZE = BLOCK_SIZE * 100;
	
    public static final String STATUSES[] = {"Downloading","Paused", "Complete", "Cancelled", "Error"};

	public static final int DOWNLOADING = 0;
	public static final int PAUSED = 1;
	public static final int COMPLETED = 2;
	public static final int CANCELLED = 3;
	public static final int ERROR = 4;

	protected Downloader(URL url, String outputFolder, int numConnections) {
		mURL = url;
		mOutputFolder = outputFolder;
		mNumConnections = numConnections;

		String fileURL = url.getFile();
		mFileName = fileURL.substring(fileURL.lastIndexOf('/') + 1);
		System.out.println("ten file: " + mFileName);
		mFileSize = -1;
		mState = DOWNLOADING;
		mDownloaded = 0;
		
		mListDownloadThread = new ArrayList<DownloadThread>();
	}

	public void pause() {
		setState(PAUSED);
	}

	public void resume() {
		setState(DOWNLOADING);
		download();
	}

	public void cancel() {
		setState(CANCELLED);
	}

	public String getURL() {
		return mURL.toString();
	}

	public int getFileSize() {
		return mFileSize;
	}
	

	public float getProgress() {
		return ((float)mDownloaded / mFileSize) * 100;
	}

	public int getState() {
		return mState;
	}

	protected void setState(int value) {
		mState = value;
		stateChanged();
	}

	protected void download() {
		Thread t = new Thread(this);
		t.start();
	}

	protected synchronized void downloaded(int value) {
		mDownloaded += value;
		stateChanged();
	}

	protected void stateChanged() {
		setChanged();
		notifyObservers();
	}

	protected abstract class DownloadThread implements Runnable {
		protected int mThreadID;
		protected URL mURL;
		protected String mOutputFile;
		protected int mStartByte;
		protected int mEndByte;
		protected boolean mIsFinished;
		protected Thread mThread;
		
		public DownloadThread(int threadID, URL url, String outputFile, int startByte, int endByte) {
			mThreadID = threadID;
			mURL = url;
			mOutputFile = outputFile;
			mStartByte = startByte;
			mEndByte = endByte;
			mIsFinished = false;
			
			download();
		}
	
		public boolean isFinished() {
			return mIsFinished;
		}
	
		public void download() {
			mThread = new Thread(this);
			mThread.start();
		}
	
		public void waitFinish() throws InterruptedException {
			mThread.join();			
		}
		
	}
}		