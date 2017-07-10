import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.text.*;

public class TextEditor extends JFrame{
	
	//Base Variables
	private JTextArea area = new JTextArea(20,120);
	private JFileChooser dialog = new JFileChooser(System.getProperty("user.dir"));
	private String currentFile = "Untitled";
	private boolean changed = false;
	
	//keylistener
		private KeyListener k1 = new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				changed = true;
				Save.setEnabled(true);
				SaveAs.setEnabled(true);
			}
		};
	
	private void readInFile(String fileName) {
		// TODO Auto-generated method stub
		try{
			FileReader r = new FileReader(fileName);
			area.read(r, null);
			r.close();
			currentFile = fileName;
			setTitle(currentFile);
			changed = false;
		}
		catch(IOException e){
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(this, "Editor can't find the file called " + fileName);
		}
	}

	private void saveOld() {
		// TODO Auto-generated method stub
		if(changed){
			if(JOptionPane.showConfirmDialog(this, "Would you like to save " + currentFile + " ?", "Save", JOptionPane.YES_NO_OPTION)== JOptionPane.YES_OPTION)
				saveFile(currentFile);
		}
};

private void saveFileAs() {
	// TODO Auto-generated method stub
	if(dialog.showSaveDialog(null)==JFileChooser.APPROVE_OPTION)
		saveFile(dialog.getSelectedFile().getAbsolutePath());
}

private void saveFile(String fileName) {
	// TODO Auto-generated method stub
	try{
		FileWriter w = new FileWriter(fileName);
		area.write(w);
		w.close();
		currentFile = fileName;
		setTitle(currentFile);
		changed = false;
		Save.setEnabled(false);
	}
	catch(IOException e){
		
	}
};
	
	
	
	//set up actions
	//open
	Action Open = new AbstractAction("Open", new ImageIcon("Icons/open.gif")){
		public void actionPerformed(ActionEvent e){
			saveOld();
			if(dialog.showOpenDialog(null)==JFileChooser.APPROVE_OPTION)
			{
				readInFile(dialog.getSelectedFile().getAbsolutePath());
			}
		}};
		
		
	//save
		Action Save = new AbstractAction("Save", new ImageIcon("Icons/save.gif")){
			public void actionPerformed(ActionEvent e){
				if(!currentFile.equals("Untitled"))
					saveFile(currentFile);
				else
					saveFileAs();
			}};

			
	//saveas
			Action SaveAs = new AbstractAction("Save as..."){
				public void actionPerformed(ActionEvent e){
					saveFileAs();
				}
			};
			
	//quit
			Action Quit = new AbstractAction("Quit"){
				public void actionPerformed(ActionEvent e){
					saveOld();
					System.exit(0);
				}
			};
			
	//actionMap
			ActionMap m = area.getActionMap();
			Action Cut = m.get(DefaultEditorKit.cutAction);
			Action Copy = m.get(DefaultEditorKit.copyAction);
			Action Paste = m.get(DefaultEditorKit.pasteAction);
			
		
	
	//Constructor
	public TextEditor(){
		
		//JScrollPane
		area.setFont(new Font("Monospaced", Font.PLAIN, 12));
		JScrollPane scroll = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		add(scroll, BorderLayout.CENTER);
		
		//JMenuBar
		JMenuBar JMB = new JMenuBar();
		setJMenuBar(JMB);
		JMenu file = new JMenu("File");
		JMenu edit = new JMenu("Edit");
		JMB.add(file);
		JMB.add(edit);

		//Set up File Tab
		//file.add(New);
		file.add(Open);
		file.add(Save);
		file.add(Quit);
		file.add(SaveAs);
		file.addSeparator();
		
		for(int i = 0; i < 4; i++)
			file.getItem(i).setIcon(null);
		
		edit.add(Cut);
		edit.add(Copy);
		edit.add(Paste);
		
		//Set up Edit Tab
		edit.getItem(0).setText("Cut");
		edit.getItem(1).setText("Copy");
		edit.getItem(2).setText("Paste");
		
		//JToolBar
		JToolBar tool = new JToolBar();
		add(tool, BorderLayout.NORTH);
		//tool.add(New);
		tool.add(Open);
		tool.add(Save);
		tool.addSeparator();
		
		JButton cut = tool.add(Cut), cop = tool.add(Copy), pas = tool.add(Paste);
		
		cut.setText(null); cut.setIcon(new ImageIcon("Icons/cut.gif"));
		cop.setText(null); cop.setIcon(new ImageIcon("Icons/copy.gif"));
		pas.setText(null); pas.setIcon(new ImageIcon("Icons/paste.gif"));
		
		Save.setEnabled(false);
		SaveAs.setEnabled(false);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		area.addKeyListener(k1);
		setTitle(currentFile);
		setVisible(true);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new TextEditor();
	}

}
