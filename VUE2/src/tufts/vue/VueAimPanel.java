package tufts.vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import edu.tufts.vue.collab.im.BasicConn;
import edu.tufts.vue.collab.im.ChatConn;
import edu.tufts.vue.collab.im.ChatConnListener;
import edu.tufts.vue.collab.im.VUEAim;

import net.kano.joscar.net.ClientConnEvent;
import net.kano.joscar.net.ClientConnListener;
import net.kano.joscar.snaccmd.FullUserInfo;
import net.kano.joscar.snaccmd.chat.ChatMsg;
import sun.net.ProgressSource.State;
import tufts.vue.gui.GUI;

/**
 * VueAimPanel
 *
 **/
public class VueAimPanel extends JPanel implements ActionListener, ClientConnListener
{
 
    private JTextField mUsernameEditor = null;
    private JPasswordField mPasswordEditor = null;   
    private PropertyPanel mPropPanel = null;
    private PropertiesEditor propertiesEditor = null;
    private VUEAim aim = null;
    private JButton loginButton = null;
    
    public VueAimPanel() {                    
        
        JPanel innerPanel = new JPanel();
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
    
        //BoxLayout boxLayout = new BoxLayout(innerPanel,BoxLayout.Y_AXIS);
        innerPanel.setLayout(gridbag);
        //mTitleEditor = new JTextField();
        mUsernameEditor = new JTextField();
        
        mPasswordEditor = new JPasswordField();
      
        
    
        mPropPanel  = new PropertyPanel();
        //mPropPanel.addProperty( "Label:", mTitleEditor); // initially Label was title
        mPropPanel.addProperty(VueResources.getString("im.username"), mUsernameEditor); //added through metadata
        mPropPanel.addProperty(VueResources.getString("im.password"), mPasswordEditor);

        JLabel titleLabel = new JLabel(VueResources.getString("im.title"));
        loginButton = new JButton(VueResources.getString("im.button.login"));
        loginButton.addActionListener(this);
        
        c.weightx = 1.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        gridbag.setConstraints(titleLabel,c);
        innerPanel.add(titleLabel);
        
        c.weightx = 1.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        gridbag.setConstraints(mPropPanel,c);
        innerPanel.add(mPropPanel);
        
        c.weightx = 1.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.NONE;
        gridbag.setConstraints(loginButton,c);
        innerPanel.add(loginButton);
        
        /**
         * JPanel metaDataLabelPanel  = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
         * metaDataLabelPanel.add(new JLabel("Metadata"));
         *
         * innerPanel.add(metaDataLabelPanel);
         */
        
        
        JPanel linePanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                g.setColor(Color.DARK_GRAY);
                g.drawLine(0,15, this.getSize().width, 15);
            }
        };
        
        //c.gridwidth = GridBagConstraints.REMAINDER;
        //c.fill = GridBagConstraints.HORIZONTAL;
        //gridbag.setConstraints(linePanel,c);
        //innerPanel.add(linePanel);
        //linePanel.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
        propertiesEditor = new PropertiesEditor(true);
        JPanel metadataPanel = new JPanel(new BorderLayout());
//         if(tufts.vue.ui.InspectorPane.META_VERSION == tufts.vue.ui.InspectorPane.OLD)
//         {
//           metadataPanel.add(propertiesEditor,BorderLayout.CENTER);
//         }
        //metadataPanel.setBorder(BorderFactory.createEmptyBorder(0,9,0,6));
        
        
         mUsernameEditor.setFont(GUI.LabelFace);
        
         // VUE 1001
         //mLocation.setFont(GUI.LabelFace);
         mPasswordEditor.setFont(GUI.LabelFace);
         
       
        
        
        c.weighty = 1.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.BOTH;
        gridbag.setConstraints(metadataPanel,c);
        innerPanel.add(metadataPanel);
        //innerPanel.add(mInfoScrollPane,BorderLayout.CENTER);
        //mInfoScrollPane.setSize( new Dimension( 200, 400));
        //mInfoScrollPane.getViewport().setLayout(new BorderLayout());
        //mInfoScrollPane.getViewport().add( innerPanel,BorderLayout.CENTER);
        //mInfoScrollPane.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        setLayout(new BorderLayout());
        //setLayout(new BorderLayout());
        //setBorder( new EmptyBorder(4,4,4,4) );
        //add(mInfoScrollPane,BorderLayout.NORTH);
        add(innerPanel,BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(10,10,0,6));
       
    }

	public void actionPerformed(ActionEvent e) {
		if (aim == null)
		{
			aim = new VUEAim(mUsernameEditor.getText(),mPasswordEditor.getText());
			aim.addConnectionListener(this);
		}
		if (aim.isConnected())
			aim.disconnect();
		
		else
		{
			aim.connect();						

		}
		
	}

	public void stateChanged(ClientConnEvent arg0) {
		if (arg0.getNewState().equals(BasicConn.STATE_CONNECTED))
			loginButton.setText("logout");
		else if (arg0.getNewState().equals(BasicConn.STATE_NOT_CONNECTED))
			loginButton.setText("login");
		
	}

	
}