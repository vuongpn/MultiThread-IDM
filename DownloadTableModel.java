import java.util.Observable;
import java.util.Observer;

import javax.swing.JProgressBar;
import javax.swing.table.AbstractTableModel;

public class DownloadTableModel extends AbstractTableModel implements Observer {
    
    private static final String[] columnNames = {"URL", "Kich thuoc (KB)","Tien do", "Trang thai"};
 
	private static final Class[] columnClasses = {String.class,String.class, JProgressBar.class, String.class};

    public void addNewDownload(Downloader download) {
       
        download.addObserver(this);
       
        fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
    }

    public void clearDownload(int row) {        
        
        fireTableRowsDeleted(row, row);
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

	public Class getColumnClass(int col) {
        return columnClasses[col];
    }
 
    public int getRowCount() {
        return DownloadManager.getInstance().getDownloadList().size();
    }

    public Object getValueAt(int row, int col) {
    	
        Downloader download = DownloadManager.getInstance().getDownloadList().get(row);
        
        switch (col) {
            case 0: 
                return download.getURL();
            case 1: 
                int size = download.getFileSize();
                return (size == -1) ? "" : (Integer.toString(size/1000));
            case 2: 
                return new Float(download.getProgress());
            case 3:
                return Downloader.STATUSES[download.getState()];
        }
        return "";
    }

    public void update(Observable o, Object arg) {
        int index = DownloadManager.getInstance().getDownloadList().indexOf(o);
        fireTableRowsUpdated(index, index);
    }
}