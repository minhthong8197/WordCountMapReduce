package ui;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import mypack.Wordcount;
import readFile.OpenFile;

public class UserInterface {
	private JFrame mainFrame;
	private JLabel inputLabel, outputLabel, statusInputLabel, statusOutputLabel, resultLabel;
	private JPanel controlPanel, controlPanel2, controlPanel3;
	private JFileChooser inputchooser, outputchooser;
	private JButton btnRun, btnGetInput, btnGetOutput, btnReset;
	private ArrayList<String> inputPath = new ArrayList<String>();
	private String outputPath = "";
	private Font font = new Font("Courier", Font.BOLD, 14);

	public static void main(String[] args) throws Exception {
		UserInterface form = new UserInterface();
		form.prepareGUI();
		form.addAllButtonListener();
	}

	private void prepareGUI() {
		mainFrame = new JFrame("Group 13 MapReduce");
		mainFrame.setSize(500, 500);
		mainFrame.setLayout(new GridLayout(3, 1));// 3 hang 1 cot
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		// thiet lap panel 1
		controlPanel = new JPanel();
		controlPanel.setBackground(Color.darkGray);
		controlPanel.setBorder(new EmptyBorder(0, 60, 10, 60));
		controlPanel.setLayout(new GridLayout(3, 1));
		// thiet lap cac control cua panel 1
		inputLabel = new JLabel("Vui lòng chọn thư mục input", JLabel.CENTER);
		inputLabel.setForeground(Color.orange);
		inputLabel.setFont(font);
		btnGetInput = new JButton("Get input");
		btnGetInput.setFont(font);
		statusInputLabel = new JLabel("Chưa có input", JLabel.CENTER);
		statusInputLabel.setForeground(Color.orange);
		// statusInputLabel.setFont(font);
		// gan control vao panel 1
		controlPanel.add(inputLabel);
		controlPanel.add(btnGetInput);
		controlPanel.add(statusInputLabel);

		// thiet lap panel 2
		controlPanel2 = new JPanel();
		controlPanel2.setBackground(Color.darkGray);
		controlPanel2.setBorder(new EmptyBorder(10, 60, 10, 60));
		controlPanel2.setLayout(new GridLayout(3, 1));
		// thiet lap cac control cua panel 2
		outputLabel = new JLabel("Vui lòng chọn nơi chứa thư mục output", JLabel.CENTER);
		outputLabel.setForeground(Color.orange);
		outputLabel.setFont(font);
		btnGetOutput = new JButton("Get output");
		btnGetOutput.setEnabled(false);
		btnGetOutput.setFont(font);
		statusOutputLabel = new JLabel("Chưa có output", JLabel.CENTER);
		statusOutputLabel.setForeground(Color.orange);
		// statusOutputLabel.setFont(font);
		// gan control vao panel 2
		controlPanel2.add(outputLabel);
		controlPanel2.add(btnGetOutput);
		controlPanel2.add(statusOutputLabel);

		// thiet lap panel 3
		controlPanel3 = new JPanel();
		controlPanel3.setBackground(Color.darkGray);
		controlPanel3.setBorder(new EmptyBorder(10, 60, 10, 60));
		controlPanel3.setLayout(new GridLayout(3, 1));
		// thiet lap cac control cua panel 2
		btnReset = new JButton("Reset");
		btnReset.setEnabled(false);
		btnReset.setFont(font);
		btnRun = new JButton("Run");
		btnRun.setEnabled(false);
		btnRun.setFont(font);
		resultLabel = new JLabel("Chưa có kết quả", JLabel.CENTER);
		resultLabel.setForeground(Color.orange);
		resultLabel.setFont(font);
		// gan control vao panel 3
		controlPanel3.add(btnReset);
		controlPanel3.add(btnRun);
		controlPanel3.add(resultLabel);

		// thiet lap cac file chooser
		inputchooser = new JFileChooser();
		inputchooser.setCurrentDirectory(new java.io.File("."));
		inputchooser.setDialogTitle("Chọn thư mục đầu vào");
		inputchooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		inputchooser.setAcceptAllFileFilterUsed(false);

		outputchooser = new JFileChooser();
		outputchooser.setCurrentDirectory(new java.io.File("."));
		outputchooser.setDialogTitle("Chọn thư mục đầu ra");
		outputchooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		outputchooser.setAcceptAllFileFilterUsed(false);

		// add vao frame
		mainFrame.add(controlPanel);// input
		mainFrame.add(controlPanel2);// output
		mainFrame.add(controlPanel3);// result
		mainFrame.setVisible(true);
	}

	private void addAllButtonListener() {
		addChooseInputListener();
		addChooseOutputListener();
		addbtnRunListener();
		addbtnResetListener();
		mainFrame.setVisible(true);
	}

	private void addbtnRunListener() {
		btnRun.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					resultLabel.setText("Vui lòng đợi giây lát");
					Wordcount.Config(inputPath, outputPath);
					resultLabel.setText("Đã chạy xong");
					OpenFile.readFile(outputPath);
				} catch (Exception e1) {
					resultLabel.setText("Chạy thất bại");
				}
			}
		});
	}

	private void addChooseInputListener() {
		btnGetInput.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int returnValue = inputchooser.showOpenDialog(mainFrame);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					try {
						inputPath.add(inputchooser.getSelectedFile().toString());
						for (int i = 0; i < inputPath.size(); i++) {
							statusInputLabel.setText(inputPath.get(i));
							//System.out.println(inputPath.get(i) + "\n");
						}
						btnGetInput.setText("Get more");
						btnGetOutput.setEnabled(true);
						btnReset.setEnabled(true);
					} catch (Exception e1) {
						System.out.println("Lỗi tại addChooseInputListener");
						// e1.printStackTrace();
					}
				} else {
					//statusInputLabel.setText("No folder was selected.");
					if (inputPath.size() == 0)
						btnGetOutput.setEnabled(false);
				}
			}
		});
	}

	private void addChooseOutputListener() {
		btnGetOutput.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int returnValue = outputchooser.showOpenDialog(mainFrame);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					try {
						outputPath = outputchooser.getSelectedFile().toString() + "\\output";
						statusOutputLabel.setText(outputPath);
						//System.out.println(outputPath);
						btnRun.setEnabled(true);
					} catch (Exception e1) {
						statusOutputLabel.setText("Thư mục output có thể đã tồn tại!");
						System.out.println("Lỗi tại addChooseOutputListener");
						// e1.printStackTrace();
					}
				} else {
					//statusInputLabel.setText("No folder was selected.");
					btnRun.setEnabled(false);
				}
			}
		});
	}
	
	private void addbtnResetListener() {
		btnReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					inputPath.clear();
					outputPath = "";
					statusInputLabel.setText("Chưa có input");
					statusOutputLabel.setText("Chưa có output");
					resultLabel.setText("Chưa có kết quả");
					btnGetInput.setText("Get input");
					btnGetOutput.setEnabled(false);
					btnRun.setEnabled(false);
					btnReset.setEnabled(false);
					
				} catch (Exception e1) {
					System.out.println("Lỗi tại addbtnResetListener");
				}
			}
		});
	}
}