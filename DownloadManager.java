import java.net.URL;
import java.util.ArrayList;

public class DownloadManager {

	private static DownloadManager sInstance = null;

	private static final int DEFAULT_NUM_CONN_PER_DOWNLOAD = 8;
	public static final String DEFAULT_OUTPUT_FOLDER = "";

	private int mNumConnPerDownload;
	private ArrayList<Downloader> mDownloadList;

	protected DownloadManager() {
		mNumConnPerDownload = DEFAULT_NUM_CONN_PER_DOWNLOAD;
		mDownloadList = new ArrayList<Downloader>();
	}

	public int getNumConnPerDownload() {
		return mNumConnPerDownload;
	}

	public void SetNumConnPerDownload(int value) {
		mNumConnPerDownload = value;
	}
	
	public Downloader getDownload(int index) {
		return mDownloadList.get(index);
	}
	
	public void removeDownload(int index) {
		mDownloadList.remove(index);
	}

	public ArrayList<Downloader> getDownloadList() {
		return mDownloadList;
	}
	
	
	public Downloader createDownload(URL verifiedURL, String outputFolder) {
		HttpDownloader fd = new HttpDownloader(verifiedURL, outputFolder, mNumConnPerDownload);
		mDownloadList.add(fd);
		
		return fd;
	}

	public static DownloadManager getInstance() {
		if (sInstance == null)
			sInstance = new DownloadManager();
		
		return sInstance;
	}

	public static URL verifyURL(String fileURL) {
		
        if (!fileURL.toLowerCase().startsWith("http://"))
            return null;
        
       
        URL verifiedUrl = null;
        try {
            verifiedUrl = new URL(fileURL);
        } catch (Exception e) {
            return null;
        }
        
       
        if (verifiedUrl.getFile().length() < 2)
            return null;
        
        return verifiedUrl;
	}

}