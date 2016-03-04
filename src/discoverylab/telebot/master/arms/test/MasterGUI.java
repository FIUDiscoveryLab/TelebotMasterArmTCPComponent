package discoverylab.telebot.master.arms.test;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

//custom imports
import static discoverylab.util.logging.LogUtils.*;
import TelebotDDSCore.Source.Java.Generated.master.arms.TMasterToArms;
import TelebotDDSCore.Source.Java.Generated.master.arms.TOPIC_MASTER_TO_SLAVE_ARMS;
import discoverylab.telebot.master.arms.TelebotMasterArmsTCPComponent;



/*
 * Provides an interface to connect and run the publisher. Also
 * provide information about the current values being received 
 * from mocap and the values being published via DDS, in this case 
 * servo positions.
 * 
 * It would serve as a debugging tool. It will be added some extra features
 * such as angle calculations, or specific values that we need to keep track on.
 */
public class MasterGUI extends JFrame implements ActionListener	{
//	private static HashMap<String, int[]> jointList = new HashMap<>();
//	private static HashMap<String, Integer> value = new HashMap<>();
//	private static HashMap<String, Integer> column = new HashMap<>();
	
	/**********************************************************************
	 * Constant Declaration
	 **********************************************************************/
	//rows values
	public static final int FIRST_VALUE = 0;
	public static final int SECOND_VALUE = 3;
	public static final int THIRD_VALUE = 6;
	
	//column values
	public static final int CURRENT_VALUE = 0;
	public static final int MIN_VALUE = 1;
	public static final int MAX_VALUE = 2;
	
	//joint values
	public static final int RW = 0;
	public static final int RE = 1;
	public static final int RS = 2;
	
	public static final int LW = 3;
	public static final int LE = 4;
	public static final int LS = 5;
	
	public static final int RW_SERVO = 0;
	public static final int RL_SERVO = 1;
	public static final int RE_SERVO = 2;
	public static final int RU_SERVO = 3;
	public static final int RF_SERVO = 4;
	public static final int RLAT_SERVO = 5;
	
	public static final int LW_SERVO = 6;
	public static final int LL_SERVO = 7;
	public static final int LE_SERVO = 8;
	public static final int LU_SERVO = 9;
	public static final int LF_SERVO = 10;
	public static final int LLAT_SERVO = 11;
	
	public static final int HF_SERVO = 12;
	public static final int HLAT_SERVO = 13;
	
	
	/**********************************************************************
	 * End Constant Declaration
	 **********************************************************************/
	
	private final String CONNECT = "Connect";
	private final String DISCONNECT = "Disconnect";
	
	//declaring objects
	public static JButton btn_connect = new JButton();
	
	public static JLabel jlb_rightWrist = new JLabel();
	public static JLabel jlb_rightElbow = new JLabel();
	public static JLabel jlb_rightShoulder = new JLabel();
	
	public static JLabel jlb_leftWrist = new JLabel();
	public static JLabel jlb_leftElbow = new JLabel();
	public static JLabel jlb_leftShoulder = new JLabel();
	
	public static JLabel jlb_firstValue = new JLabel();
	public static JLabel jlb_secondValue = new JLabel();
	public static JLabel jlb_thirdValue = new JLabel();
	
	public static JLabel jlb_firstCurrentValue = new JLabel();
	public static JLabel jlb_secondCurrentValue = new JLabel();
	public static JLabel jlb_thirdCurrentValue = new JLabel();
	
	public static JLabel jlb_firstMinValue = new JLabel();
	public static JLabel jlb_secondMinValue = new JLabel();
	public static JLabel jlb_thirdMinValue = new JLabel();
	
	public static JLabel jlb_firstMaxValue = new JLabel();
	public static JLabel jlb_secondMaxValue = new JLabel();
	public static JLabel jlb_thirdMaxValue = new JLabel();
	
	//====================Servo Labels=========================
	
	public static JLabel jlb_servoRightWrist = new JLabel();
	public static JLabel jlb_servoRightLower = new JLabel();
	public static JLabel jlb_servoRightElbow = new JLabel();
	public static JLabel jlb_servoRightUpper= new JLabel();
	public static JLabel jlb_servoRightFront = new JLabel();
	public static JLabel jlb_servoRightLateral= new JLabel();
	
	public static JLabel jlb_servoLeftWrist = new JLabel();
	public static JLabel jlb_servoLeftLower = new JLabel();
	public static JLabel jlb_servoLeftElbow = new JLabel();
	public static JLabel jlb_servoLeftUpper= new JLabel();
	public static JLabel jlb_servoLeftFront = new JLabel();
	public static JLabel jlb_servoLeftLateral= new JLabel();
	
	public static JLabel jlb_servoHeadFront= new JLabel();
	public static JLabel jlb_servoHeadLateral= new JLabel();
	
	public static JLabel jlb_servoHeader= new JLabel();
	public static JLabel jlb_idHeader= new JLabel();
	public static JLabel jlb_sentHeader= new JLabel();
	public static JLabel jlb_actualHeader= new JLabel();
	
	//Array containing the jlabels for the different values
	public static JLabel[][] jlb_sensor_array = new JLabel[9][6];
	
	/***********************************************************************************
	 * Indexes inside the jlb_sensor_array
	 * *********************************************************************************
	 *                    |    First Value    ||   Second Value    ||    Third Value    |
	 * ---------------------------------------------------------------------------------
	 *                |   |Current| Min | Max ||Current| Min | Max ||Current| Min | Max |
	 * ---------------------------------------------------------------------------------
	 *                |   |   0   |  1  |  2  ||   3   |  4  |  5  ||   6   |  7  |  8  |
	 * ---------------------------------------------------------------------------------
	 * Right|Wrist    | 0 | 	  |	    |     ||       |     |     ||       |     |     | 
	 * 		----------------------------------------------------------------------------
	 * 		|Elbow 	  |	1 | 	  |	    |     ||       |     |     ||       |     |     |
	 *      ----------------------------------------------------------------------------
	 * 		|Shoulder | 2 | 	  |	    |     ||       |     |     ||       |     |     |
	 * ---------------------------------------------------------------------------------
	 * Left |Wrist    | 3 | 	  |	    |     ||       |     |     ||       |     |     |
	 * 		----------------------------------------------------------------------------
	 * 		|Elbow    | 4 | 	  |	    |     ||       |     |     ||       |     |     |
	 *  	----------------------------------------------------------------------------
	 * 		|Shoulder | 5 | 	  |	    |     ||       |     |     ||       |     |     |
	 * ********************************************************************************* 
	 */
	
	public static JLabel[][] jlb_servos_array = new JLabel[3][14];
	
	/***********************************************************************************
	 * Indexes inside the jlb_servos_array
	 * *********************************************************************************
	 *      Servo     |   | Id | Pos_sent | Actual_Pos |
	 * ---------------------------------------------------------------------------------
	 *                |   |  0 |     1    |     2      | 
	 * ---------------------------------------------------------------------------------
	 * Right Wrist    | 0 |    |          |            |	  
	 * ---------------------------------------------------------------------------------
	 * Right Lower 	  | 1 |    |          |            | 	 
	 * ---------------------------------------------------------------------------------
	 * Right Elbow    | 2 |    |          |            | 	 
	 * ---------------------------------------------------------------------------------
	 * Right Upper    | 3 |    |          |            | 	  
	 * ---------------------------------------------------------------------------------
	 * Right Front    | 4 |    |          |            | 	   
	 * ---------------------------------------------------------------------------------
	 * Right Lateral  | 5 |    |          |            | 
	 * ---------------------------------------------------------------------------------
	 * Left Wrist     | 6 |    |          |            | 	  
	 * ---------------------------------------------------------------------------------
	 * Left Lower 	  | 7 |    |          |            | 	 
	 * ---------------------------------------------------------------------------------
	 * Left Elbow     | 8 |    |          |            |	 
	 * ---------------------------------------------------------------------------------
	 * Left Upper     | 9 |    |          |            |	  
	 * ---------------------------------------------------------------------------------
	 * Left Front     |10 |    |          |            | 	   
	 * ---------------------------------------------------------------------------------
	 * Left Lateral   |11 |    |          |            | 
	 * ---------------------------------------------------------------------------------
	 * Head Front     |12 |    |          |            | 	   
	 * ---------------------------------------------------------------------------------
	 * Head Lateral   |13 |    |          |            | 	 	 
	 * ********************************************************************************* 
	 */
	
	MasterGUI(){
		super("Master GUI");
		Initiate();
		
	}
	
	private void Initiate(){		
		
		
		
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints btn_gbConstraints = new GridBagConstraints();
		
		GridBagConstraints jlb_rightWrist_gbConstraints = new GridBagConstraints();
		GridBagConstraints jlb_rightElbow_gbConstraints = new GridBagConstraints();
		GridBagConstraints jlb_rightShoulder_gbConstraints = new GridBagConstraints();
		
		GridBagConstraints jlb_leftWrist_gbConstraints = new GridBagConstraints();
		GridBagConstraints jlb_leftElbow_gbConstraints = new GridBagConstraints();
		GridBagConstraints jlb_leftShoulder_gbConstraints = new GridBagConstraints();
		
		GridBagConstraints jlb_firstValue_gbConstraints = new GridBagConstraints();
		GridBagConstraints jlb_secondValue_gbConstraints = new GridBagConstraints();
		GridBagConstraints jlb_thirdValue_gbConstraints = new GridBagConstraints();
		
		GridBagConstraints jlb_firstCurrentValue_gbConstraints = new GridBagConstraints();
		GridBagConstraints jlb_secondCurrentValue_gbConstraints = new GridBagConstraints();
		GridBagConstraints jlb_thirdCurrentValue_gbConstraints = new GridBagConstraints();
		
		GridBagConstraints jlb_firstMaxValue_gbConstraints = new GridBagConstraints();
		GridBagConstraints jlb_secondMaxValue_gbConstraints = new GridBagConstraints();
		GridBagConstraints jlb_thirdMaxValue_gbConstraints = new GridBagConstraints();
		
		GridBagConstraints jlb_firstMinValue_gbConstraints = new GridBagConstraints();
		GridBagConstraints jlb_secondMinValue_gbConstraints = new GridBagConstraints();
		GridBagConstraints jlb_thirdMinValue_gbConstraints = new GridBagConstraints();
		
		GridBagConstraints jlb_servoRightWrist_gbConstraints = new GridBagConstraints();
		GridBagConstraints jlb_servoRightLower_gbConstraints = new GridBagConstraints();
		GridBagConstraints jlb_servoRightElbow_gbConstraints = new GridBagConstraints();
		GridBagConstraints jlb_servoRightUpper_gbConstraints = new GridBagConstraints();
		GridBagConstraints jlb_servoRightFront_gbConstraints = new GridBagConstraints();
		GridBagConstraints jlb_servoRightLateral_gbConstraints = new GridBagConstraints();
		
		GridBagConstraints jlb_servoLeftWrist_gbConstraints = new GridBagConstraints();
		GridBagConstraints jlb_servoLeftLower_gbConstraints = new GridBagConstraints();
		GridBagConstraints jlb_servoLeftElbow_gbConstraints = new GridBagConstraints();	
		GridBagConstraints jlb_servoLeftUpper_gbConstraints = new GridBagConstraints();
		GridBagConstraints jlb_servoLeftFront_gbConstraints = new GridBagConstraints();
		GridBagConstraints jlb_servoLeftLateral_gbConstraints = new GridBagConstraints();
		
		
		GridBagConstraints jlb_servoHeadLateral_gbConstraints = new GridBagConstraints();
		GridBagConstraints jlb_servoHeadFront_gbConstraints = new GridBagConstraints();
		
		GridBagConstraints jlb_servoHeader_gbConstraints = new GridBagConstraints();
		GridBagConstraints jlb_idHeader_gbConstraints = new GridBagConstraints();		
		GridBagConstraints jlb_sentHeader_gbConstraints = new GridBagConstraints();
		GridBagConstraints jlb_actualHeader_gbConstraints = new GridBagConstraints();
		
		GridBagConstraints jlb_generic = new GridBagConstraints();
		
		
		/*
		 * Setting components constraints
		 */
		//button constraints 
		btn_gbConstraints.gridx =11;
		btn_gbConstraints.gridy =16;
		
		/***********************************************************************************
		 * labels constraints
		 * **
		 * Sensor Values Table: starting x = 0 ; y = 0
		 ***********************************************************************************/
		//first column
		jlb_rightWrist_gbConstraints.gridx = 0;
		jlb_rightWrist_gbConstraints.gridy = 2;	
		jlb_rightWrist_gbConstraints.insets = new Insets(5, 5, 5, 5);
		
		jlb_rightElbow_gbConstraints.gridx = 0;
		jlb_rightElbow_gbConstraints.gridy = 3;
		jlb_rightElbow_gbConstraints.insets = new Insets(5, 5, 5, 5);
		
		jlb_rightShoulder_gbConstraints.gridx = 0;
		jlb_rightShoulder_gbConstraints.gridy = 4;
		jlb_rightShoulder_gbConstraints.insets = new Insets(5, 5, 5, 5);

		jlb_leftWrist_gbConstraints.gridx = 0;
		jlb_leftWrist_gbConstraints.gridy = 5;	
		jlb_leftWrist_gbConstraints.insets = new Insets(5, 5, 5, 5);

		
		jlb_leftElbow_gbConstraints.gridx = 0;
		jlb_leftElbow_gbConstraints.gridy = 6;
		jlb_leftElbow_gbConstraints.insets = new Insets(5, 5, 5, 5);

		
		jlb_leftShoulder_gbConstraints.gridx = 0;
		jlb_leftShoulder_gbConstraints.gridy = 7;
		jlb_leftShoulder_gbConstraints.insets = new Insets(5, 5, 5, 5);

		
		//first row
		jlb_firstValue_gbConstraints.gridx = 1;
		jlb_firstValue_gbConstraints.gridy = 0;
		jlb_firstValue_gbConstraints.insets = new Insets(5, 50, 5, 50);
		jlb_firstValue_gbConstraints.gridwidth = 3;
		
		
		jlb_secondValue_gbConstraints.gridx = 4;
		jlb_secondValue_gbConstraints.gridy = 0;
		jlb_secondValue_gbConstraints.insets = new Insets(5, 50, 5, 50);
		jlb_secondValue_gbConstraints.gridwidth = 3;
		
		jlb_thirdValue_gbConstraints.gridx = 7;
		jlb_thirdValue_gbConstraints.gridy = 0;
		jlb_thirdValue_gbConstraints.insets = new Insets(5, 50, 5, 50);
		jlb_thirdValue_gbConstraints.gridwidth = 3;		
		
		//second row
		jlb_firstCurrentValue_gbConstraints.gridx = 1;
		jlb_firstCurrentValue_gbConstraints.gridy = 1;
		jlb_firstCurrentValue_gbConstraints.ipadx = GridBagConstraints.CENTER;
		
		jlb_firstMinValue_gbConstraints.gridx = 2;
		jlb_firstMinValue_gbConstraints.gridy = 1;
		jlb_firstMinValue_gbConstraints.ipadx = GridBagConstraints.CENTER;
		
		jlb_firstMaxValue_gbConstraints.gridx = 3;
		jlb_firstMaxValue_gbConstraints.gridy = 1;
		jlb_firstMaxValue_gbConstraints.ipadx = GridBagConstraints.CENTER;

		
		jlb_secondCurrentValue_gbConstraints.gridx = 4;
		jlb_secondCurrentValue_gbConstraints.gridy = 1;
		jlb_secondCurrentValue_gbConstraints.ipadx = GridBagConstraints.CENTER;

		
		jlb_secondMinValue_gbConstraints.gridx = 5;
		jlb_secondMinValue_gbConstraints.gridy = 1;
		jlb_secondMinValue_gbConstraints.ipadx = GridBagConstraints.CENTER;

		
		jlb_secondMaxValue_gbConstraints.gridx = 6;
		jlb_secondMaxValue_gbConstraints.gridy = 1;
		jlb_secondMaxValue_gbConstraints.ipadx = GridBagConstraints.CENTER;

		
		jlb_thirdCurrentValue_gbConstraints.gridx = 7;
		jlb_thirdCurrentValue_gbConstraints.gridy = 1;
		jlb_thirdCurrentValue_gbConstraints.ipadx = GridBagConstraints.CENTER;

		
		jlb_thirdMinValue_gbConstraints.gridx = 8;
		jlb_thirdMinValue_gbConstraints.gridy = 1;
		jlb_thirdMinValue_gbConstraints.ipadx = GridBagConstraints.CENTER;

		
		jlb_thirdMaxValue_gbConstraints.gridx = 9;
		jlb_thirdMaxValue_gbConstraints.gridy = 1;
		jlb_thirdMaxValue_gbConstraints.ipadx = GridBagConstraints.CENTER;
		
		/***********************************************************************************
		 * END OF: Sensor Values Table
		 * **
		 * Servo Values Table: starting x = 11 ; y = 0
		 ***********************************************************************************/
		//==================Headers Row==================
		jlb_servoHeader_gbConstraints.gridx = 11;
		jlb_servoHeader_gbConstraints.gridy = 0;
		jlb_servoHeader_gbConstraints.insets = new Insets(5, 100, 5, 5);
		
		jlb_idHeader_gbConstraints.gridx = 12;
		jlb_idHeader_gbConstraints.gridy = 0;
		jlb_idHeader_gbConstraints.insets = new Insets(5, 5, 5, 5);
		
		jlb_sentHeader_gbConstraints.gridx = 13;
		jlb_sentHeader_gbConstraints.gridy = 0;
		jlb_sentHeader_gbConstraints.insets = new Insets(5, 5, 5, 5);
		
		jlb_actualHeader_gbConstraints.gridx = 14;
		jlb_actualHeader_gbConstraints.gridy = 0;
		jlb_actualHeader_gbConstraints.insets = new Insets(5, 5, 5, 5);
		
		//==================First Column==================
		jlb_servoRightWrist_gbConstraints.gridx = 11;
		jlb_servoRightWrist_gbConstraints.gridy = 1;
		jlb_servoRightWrist_gbConstraints.insets = new Insets(5, 100, 5, 5);
		
		jlb_servoRightLower_gbConstraints.gridx = 11;
		jlb_servoRightLower_gbConstraints.gridy = 2;
		jlb_servoRightLower_gbConstraints.insets = new Insets(5, 100, 5, 5);
		
		jlb_servoRightElbow_gbConstraints.gridx = 11;
		jlb_servoRightElbow_gbConstraints.gridy = 3;
		jlb_servoRightElbow_gbConstraints.insets = new Insets(5, 100, 5, 5);
		
		jlb_servoRightUpper_gbConstraints.gridx = 11;
		jlb_servoRightUpper_gbConstraints.gridy = 4;
		jlb_servoRightUpper_gbConstraints.insets = new Insets(5, 100, 5, 5);
		
		jlb_servoRightFront_gbConstraints.gridx = 11;
		jlb_servoRightFront_gbConstraints.gridy = 5;
		jlb_servoRightFront_gbConstraints.insets = new Insets(5, 100, 5, 5);
		
		jlb_servoRightLateral_gbConstraints.gridx = 11;
		jlb_servoRightLateral_gbConstraints.gridy = 6;
		jlb_servoRightLateral_gbConstraints.insets = new Insets(5, 100, 5, 5);
		
		jlb_servoLeftWrist_gbConstraints.gridx = 11;
		jlb_servoLeftWrist_gbConstraints.gridy = 7;
		jlb_servoLeftWrist_gbConstraints.insets = new Insets(5, 100, 5, 5);
		
		jlb_servoLeftLower_gbConstraints.gridx = 11;
		jlb_servoLeftLower_gbConstraints.gridy = 8;
		jlb_servoLeftLower_gbConstraints.insets = new Insets(5, 100, 5, 5);
		
		jlb_servoLeftElbow_gbConstraints.gridx = 11;
		jlb_servoLeftElbow_gbConstraints.gridy = 9;
		jlb_servoLeftElbow_gbConstraints.insets = new Insets(5, 100, 5, 5);
		
		jlb_servoLeftUpper_gbConstraints.gridx = 11;
		jlb_servoLeftUpper_gbConstraints.gridy = 10;
		jlb_servoLeftUpper_gbConstraints.insets = new Insets(5, 100, 5, 5);
		
		jlb_servoLeftFront_gbConstraints.gridx = 11;
		jlb_servoLeftFront_gbConstraints.gridy = 11;
		jlb_servoLeftFront_gbConstraints.insets = new Insets(5, 100, 5, 5);
		
		jlb_servoLeftLateral_gbConstraints.gridx = 11;
		jlb_servoLeftLateral_gbConstraints.gridy = 12;
		jlb_servoLeftLateral_gbConstraints.insets = new Insets(5, 100, 5, 5);
		
		jlb_servoHeadFront_gbConstraints.gridx = 11;
		jlb_servoHeadFront_gbConstraints.gridy = 13;
		jlb_servoHeadFront_gbConstraints.insets = new Insets(5, 100, 5, 5);
		
		jlb_servoHeadLateral_gbConstraints.gridx = 11;
		jlb_servoHeadLateral_gbConstraints.gridy = 14;
		jlb_servoHeadLateral_gbConstraints.insets = new Insets(5, 100, 5, 5);
		
		/***********************************************************************************
		 * End of label constraints
		 ***********************************************************************************/

		//=============== Initializing Sensors Array ===============
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 6; j++) {
				jlb_sensor_array[i][j] = new JLabel();
			}
			
		}
		//Creating, creating constraints and adding labels for values
		for (int i = 1; i < 10; i++) {
			for (int j = 2; j < 8; j++) {
				jlb_generic.gridx = i;
				jlb_generic.gridy = j;
				jlb_generic.ipadx = GridBagConstraints.CENTER;

				this.add(jlb_sensor_array[i-1][j-2], jlb_generic);
						
			}
			
		}
		
		//=============== Initializing Servos Array ===============
		
		
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 14; j++) {
						jlb_servos_array[i][j] = new JLabel();
					}
					
				}
				//Creating, creating constraints and adding labels for values
				for (int i = 12; i < 15; i++) {
					for (int j = 1; j < 15; j++) {
						jlb_generic.gridx = i;
						jlb_generic.gridy = j;
						jlb_generic.ipadx = GridBagConstraints.CENTER;

						this.add(jlb_servos_array[i-12][j-1], jlb_generic);
								
					}
					
				}
		
		//configuring connect button/ listener
		btn_connect.setText(CONNECT);
		btn_connect.addActionListener(this);
		
		//adding text to labels
		jlb_rightWrist.setText("Right Wrist");
		jlb_rightElbow.setText("Right Elbow");
		jlb_rightShoulder.setText("Right Shoulder");
		
		jlb_leftWrist.setText("Left Wrist");
		jlb_leftElbow.setText("Left Elbow");
		jlb_leftShoulder.setText("Left Shoulder");
		
		jlb_firstValue.setText("First Value");
		jlb_secondValue.setText("Second Value");
		jlb_thirdValue.setText("Third Value");
		
		jlb_firstCurrentValue.setText("Current");
		jlb_secondCurrentValue.setText("Current");
		jlb_thirdCurrentValue.setText("Current");
		
		jlb_firstMinValue.setText("Min");
		jlb_secondMinValue.setText("Min");
		jlb_thirdMinValue.setText("Min");
		
		jlb_firstMaxValue.setText("Max");
		jlb_secondMaxValue.setText("Max");
		jlb_thirdMaxValue.setText("Max");
		
		jlb_actualHeader.setText("Actual");
		jlb_servoHeader.setText("Servo");
		jlb_idHeader.setText("ID");
		jlb_sentHeader.setText("Sent");
		
		jlb_servoRightWrist.setText("RW");
		jlb_servoRightLower.setText("RL");
		jlb_servoRightElbow.setText("RE");
		jlb_servoRightUpper.setText("RU");
		jlb_servoRightFront.setText("RF");
		jlb_servoRightLateral.setText("RLat");
		
		jlb_servoLeftWrist.setText("LW");
		jlb_servoLeftLower.setText("LL");
		jlb_servoLeftElbow.setText("LE");
		jlb_servoLeftUpper.setText("LU");
		jlb_servoLeftFront.setText("LF");
		jlb_servoLeftLateral.setText("LLat");
		
		jlb_servoHeadFront.setText("HF");
		jlb_servoHeadLateral.setText("HL");
		//adding components to the frame
		this.add(btn_connect,btn_gbConstraints);
		
		this.add(jlb_rightWrist,jlb_rightWrist_gbConstraints);
		this.add(jlb_rightElbow,jlb_rightElbow_gbConstraints);
		this.add(jlb_rightShoulder,jlb_rightShoulder_gbConstraints);
		
		this.add(jlb_leftWrist,jlb_leftWrist_gbConstraints);
		this.add(jlb_leftElbow,jlb_leftElbow_gbConstraints);
		this.add(jlb_leftShoulder,jlb_leftShoulder_gbConstraints);
		
		this.add(jlb_firstValue,jlb_firstValue_gbConstraints);
		this.add(jlb_secondValue,jlb_secondValue_gbConstraints);
		this.add(jlb_thirdValue,jlb_thirdValue_gbConstraints);
		
		this.add(jlb_firstCurrentValue,jlb_firstCurrentValue_gbConstraints);
		this.add(jlb_secondCurrentValue,jlb_secondCurrentValue_gbConstraints);
		this.add(jlb_thirdCurrentValue,jlb_thirdCurrentValue_gbConstraints);
		
		this.add(jlb_firstMinValue,jlb_firstMinValue_gbConstraints);
		this.add(jlb_secondMinValue,jlb_secondMinValue_gbConstraints);
		this.add(jlb_thirdMinValue,jlb_thirdMinValue_gbConstraints);
		
		this.add(jlb_firstMaxValue,jlb_firstMaxValue_gbConstraints);
		this.add(jlb_secondMaxValue,jlb_secondMaxValue_gbConstraints);
		this.add(jlb_thirdMaxValue,jlb_thirdMaxValue_gbConstraints);
		
		this.add(jlb_servoHeader,jlb_servoHeader_gbConstraints);
		this.add(jlb_idHeader,jlb_idHeader_gbConstraints);
		this.add(jlb_sentHeader,jlb_sentHeader_gbConstraints);
		this.add(jlb_actualHeader,jlb_actualHeader_gbConstraints);
		
		this.add(jlb_servoRightWrist,jlb_servoRightWrist_gbConstraints);
		this.add(jlb_servoRightLower,jlb_servoRightLower_gbConstraints);
		this.add(jlb_servoRightElbow,jlb_servoRightElbow_gbConstraints);
		this.add(jlb_servoRightUpper,jlb_servoRightUpper_gbConstraints);
		this.add(jlb_servoRightFront,jlb_servoRightFront_gbConstraints);
		this.add(jlb_servoRightLateral,jlb_servoRightLateral_gbConstraints);
		
		this.add(jlb_servoLeftWrist,jlb_servoLeftWrist_gbConstraints);
		this.add(jlb_servoLeftLower,jlb_servoLeftLower_gbConstraints);
		this.add(jlb_servoLeftElbow,jlb_servoLeftElbow_gbConstraints);
		this.add(jlb_servoLeftUpper,jlb_servoLeftUpper_gbConstraints);
		this.add(jlb_servoLeftFront,jlb_servoLeftFront_gbConstraints);
		this.add(jlb_servoLeftLateral,jlb_servoLeftLateral_gbConstraints);
		
		this.add(jlb_servoHeadLateral,jlb_servoHeadLateral_gbConstraints);
		this.add(jlb_servoHeadFront,jlb_servoHeadFront_gbConstraints);
		
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(btn_connect.getText() == CONNECT){
			// 1. INITIATE Slave Component DEVICE
			TelebotMasterArmsTCPComponent telebotMasterArms = new TelebotMasterArmsTCPComponent(6666);
			telebotMasterArms.initiate();
			
			// 2. INITIATE Transmission PROTOCOL
			telebotMasterArms.initiateTransmissionProtocol(TOPIC_MASTER_TO_SLAVE_ARMS.VALUE
					, TMasterToArms.class);
			
			// 3. INITATE DataWriter
			telebotMasterArms.initiateDataWriter();
			
			btn_connect.setText(DISCONNECT);
			
			
			
			
			
		}
		
	}
	
	public static  void setSensorLabelValues(String text,int joint, int value, int column ) {
		try{
			jlb_sensor_array[joint][value + column].setText(text);
		}
		catch(Exception e){
			
		}
		
	}
	
	public static  void setServosLabelValues(int servo, int servoId, int valueSent,int actualValue ) {
		try{
			jlb_servos_array[servo][0].setText(Integer.toString(servoId));
			jlb_servos_array[servo][1].setText(Integer.toString(valueSent));
			jlb_servos_array[servo][2].setText(Integer.toString(actualValue));
		}
		catch(Exception e){
			
		}
		
	}
}
