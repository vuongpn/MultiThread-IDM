import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import java.awt.Component;

public class DownloadManagerGUI extends javax.swing.JFrame implements Observer{

	
	private DownloadTableModel mTableModel;
	
	private Downloader mSelectedDownloader;
	
	private boolean mIsClearing;

    public DownloadManagerGUI() {
    	mTableModel = new DownloadTableModel();
        initComponents();
        initialize();
    }
    
    private void initialize() {
    
    	jtbDownload.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                tableSelectionChanged();
            }
        });
    
    	jtbDownload.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
        ProgressRenderer renderer = new ProgressRenderer(0, 100);
        renderer.setStringPainted(true); 
        jtbDownload.setDefaultRenderer(JProgressBar.class, renderer);
      
        jtbDownload.setRowHeight(
                (int) renderer.getPreferredSize().getHeight());
    }

    private void initComponents() {

        jtxURL = new javax.swing.JTextField();
        jbnAdd = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbDownload = new javax.swing.JTable();
        jbnPause = new javax.swing.JButton();
        jbnRemove = new javax.swing.JButton();
        jbnCancel = new javax.swing.JButton();
        jbnExit = new javax.swing.JButton();
        jbnResume = new javax.swing.JButton();
        jbnOpen= new javax.swing.JButton();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Download Manager by TranTungVuong");
        setResizable(false);

        jbnAdd.setText("Bat dau Download");
        jbnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnAddActionPerformed(evt);
            }
        });
        jbnOpen.setText("Mo folder");
        jbnOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	File foler = new File("C:\\Users\\KimAnh\\eclipse-workspace\\DownloadManager\\"); // path to the directory to be opened
                Desktop desktop = null;
                if (Desktop.isDesktopSupported()) {
                desktop = Desktop.getDesktop();
                }

                try {
                desktop.open(foler);
                } catch (IOException e) {
                }
            }
        });
        jtbDownload.setModel(mTableModel);
        jScrollPane1.setViewportView(jtbDownload);

        jbnPause.setText("Dung");
        jbnPause.setEnabled(false);
        jbnPause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnPauseActionPerformed(evt);
            }
        });

        jbnRemove.setText("Xoa");
        jbnRemove.setEnabled(false);
        jbnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnRemoveActionPerformed(evt);
            }
        });

        jbnCancel.setText("Huy");
        jbnCancel.setEnabled(false);
        jbnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnCancelActionPerformed(evt);
            }
        });

        jbnExit.setText("Thoat");
        jbnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnExitActionPerformed(evt);
            }
        });

        jbnResume.setText("Tiep tuc");
        jbnResume.setEnabled(false);
        jbnResume.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnResumeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(jbnPause, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
        					.addGap(18)
        					.addComponent(jbnResume, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(jbnCancel, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
        					.addGap(18)
        					.addComponent(jbnOpen, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(jbnRemove, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED, 405, Short.MAX_VALUE)
        					.addComponent(jbnExit, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE))
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(jtxURL, GroupLayout.DEFAULT_SIZE, 990, Short.MAX_VALUE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(jbnAdd))
        				.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 1115, Short.MAX_VALUE))
        			.addContainerGap())
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jtxURL, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(jbnAdd))
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jbnPause)
        				.addComponent(jbnExit)
        				.addComponent(jbnResume)
        				.addComponent(jbnCancel)
        				.addComponent(jbnOpen)
        				.addComponent(jbnRemove))
        			.addContainerGap())
        );
        layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {jbnPause, jbnRemove, jbnCancel, jbnExit, jbnResume, jbnOpen});
        getContentPane().setLayout(layout);

        pack();
    }
    private void jbnPauseActionPerformed(java.awt.event.ActionEvent evt) {
    	mSelectedDownloader.pause();
        updateButtons();
    }

    private void jbnResumeActionPerformed(java.awt.event.ActionEvent evt) {
    	mSelectedDownloader.resume();
        updateButtons();
    }

    private void jbnCancelActionPerformed(java.awt.event.ActionEvent evt) {
    	mSelectedDownloader.cancel();
        updateButtons();
    }

    private void jbnRemoveActionPerformed(java.awt.event.ActionEvent evt) {
    	mIsClearing = true;
    	int index = jtbDownload.getSelectedRow();
    	DownloadManager.getInstance().removeDownload(index);
    	mTableModel.clearDownload(index);
        mIsClearing = false;
        mSelectedDownloader = null;
        updateButtons();
    }
    private void jbnExitActionPerformed(java.awt.event.ActionEvent evt) {
        setVisible(false);
    }

    private void jbnAddActionPerformed(java.awt.event.ActionEvent evt) {
    	URL verifiedUrl = DownloadManager.verifyURL(jtxURL.getText());
        if (verifiedUrl != null) {
        	Downloader download = DownloadManager.getInstance().createDownload(verifiedUrl, 
        			DownloadManager.DEFAULT_OUTPUT_FOLDER);
        	mTableModel.addNewDownload(download);
        	jtxURL.setText(""); 
        } else {
            JOptionPane.showMessageDialog(this,
                    "URl khong hop le", "co loi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    private void tableSelectionChanged() {
    	
        if (mSelectedDownloader != null)
        	mSelectedDownloader.deleteObserver(DownloadManagerGUI.this);
    
        if (!mIsClearing) {
        	int index = jtbDownload.getSelectedRow();
        	if (index != -1) {
	        	mSelectedDownloader = DownloadManager.getInstance().getDownload(jtbDownload.getSelectedRow());
	        	mSelectedDownloader.addObserver(DownloadManagerGUI.this);
        	} else
        		mSelectedDownloader = null;
            updateButtons();
        }
    }
    
    @Override
	public void update(Observable o, Object arg) {
    	
        if (mSelectedDownloader != null && mSelectedDownloader.equals(o))
            updateButtons();
	}
  
    private void updateButtons() {
        if (mSelectedDownloader != null) {
            int state = mSelectedDownloader.getState();
            switch (state) {
                case Downloader.DOWNLOADING:
                    jbnPause.setEnabled(true);
                    jbnResume.setEnabled(false);
                    jbnCancel.setEnabled(true);
                    jbnRemove.setEnabled(false);
                    break;
                case Downloader.PAUSED:
                	jbnPause.setEnabled(false);
                	jbnResume.setEnabled(true);
                	jbnCancel.setEnabled(true);
                	jbnRemove.setEnabled(false);
                    break;
                case Downloader.ERROR:
                	jbnPause.setEnabled(false);
                	jbnResume.setEnabled(true);
                	jbnCancel.setEnabled(false);
                	jbnRemove.setEnabled(true);
                    break;
                default: 
                	jbnPause.setEnabled(false);
                	jbnResume.setEnabled(false);
                	jbnCancel.setEnabled(false);
                	jbnRemove.setEnabled(true);
            }
        } else {
           
        	jbnPause.setEnabled(false);
        	jbnResume.setEnabled(false);
        	jbnCancel.setEnabled(false);
        	jbnRemove.setEnabled(false);
        }
    }

    public static void main(String args[]) {
    	
    	try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DownloadManagerGUI().setVisible(true);
            }
        });
    }

  
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbnAdd;
    private javax.swing.JButton jbnCancel;
    private javax.swing.JButton jbnExit;
    private javax.swing.JButton jbnPause;
    private javax.swing.JButton jbnRemove;
    private javax.swing.JButton jbnResume;
    private javax.swing.JTable jtbDownload;
    private javax.swing.JButton jbnOpen;
    private javax.swing.JTextField jtxURL;
   
}