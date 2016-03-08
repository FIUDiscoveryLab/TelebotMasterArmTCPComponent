package discoverylab.telebot.master.arms.gui;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TelebotMasterArmsTCPView extends JFrame 
{
	private JLabel headLabel = new JLabel("Head: ");
	private JLabel leftShoulderLabel = new JLabel("Left Shoulder: ");
	private JLabel leftElbowLabel = new JLabel("Left Elbow: ");
	private JLabel leftWristLabel = new JLabel("Left Wrist: ");
	private JLabel rightShoulderLabel = new JLabel("Right Shoulder: ");
	private JLabel rightElbowLabel = new JLabel("Right Elbow: ");
	private JLabel rightWristLabel = new JLabel("Right Wrist: ");
	
	private JTextField headText = new JTextField();
	private JTextField leftShoulderText = new JTextField();
	private JTextField leftElbowText = new JTextField();
	private JTextField leftWristText = new JTextField();
	private JTextField rightShoulderText = new JTextField();
	private JTextField rightElbowText = new JTextField();
	private JTextField rightWristText = new JTextField();

	public TelebotMasterArmsTCPView()
	{
//		GridLayout gridLayout = new GridLayout(14, 2, 1, 1)
		
		JPanel viewPanel = new JPanel(new GridLayout(14, 2, 1, 1));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(400, 700);
		
//		viewPanel.setLayout(gridLayout);
		viewPanel.add(headLabel);
		viewPanel.add(headText);
		viewPanel.add(leftShoulderLabel);
		viewPanel.add(leftShoulderText);
		viewPanel.add(leftElbowLabel);
		viewPanel.add(leftElbowText);
		viewPanel.add(leftWristLabel);
		viewPanel.add(leftWristText);
		viewPanel.add(rightShoulderLabel);
		viewPanel.add(rightShoulderText);
		viewPanel.add(rightElbowLabel);
		viewPanel.add(rightElbowText);
		viewPanel.add(rightWristLabel);
		viewPanel.add(rightWristText);
		this.add(viewPanel);
	}
	

	public String getHeadText() {
		return headText.getText();
	}

	public String getLeftShoulderText() {
		return leftShoulderText.getText();
	}

	public String getLeftElbowText() {
		return leftElbowText.getText();
	}

	public String getLeftWristText() {
		return leftWristText.getText();
	}

	public String getRightShoulderText() {
		return rightShoulderText.getText();
	}

	public String getRightElbowText() {
		return rightElbowText.getText();
	}

	public String getRightWristText() {
		return rightWristText.getText();
	}

	public void setHeadText(String text)
	{
		headText.setText(text);
	}
	
	public void setLeftShoulderText(String text)
	{
		leftShoulderText.setText(text);
	}
	
	public void setLeftElbowText(String text)
	{
		leftElbowText.setText(text);
	}
	
	public void setLeftWristText(String text)
	{
		leftWristText.setText(text);
	}
	
	public void setRightShoulderText(String text)
	{
		rightShoulderText.setText(text);
	}
	
	public void setRightElbowText(String text)
	{
		rightElbowText.setText(text);
	}
	
	public void setRightWristText(String text)
	{
		rightWristText.setText(text);
	}
}
