package fr.fourmond.jerome.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.beans.Statement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fr.fourmond.jerome.framework.Vertex;

public class EditVertexView<T extends Vertex> extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	private T vertex;
	private Class<?> C;
	private int nbFields;
	
	private static JPanel main_panel;
		private static JPanel center_panel;
			private static List<JLabel> labels;
			private static List<JTextField> fields;
			private static List<Class<?>> types;
		private static JPanel bottom_panel;
			private static JButton confirm;
	
	private static Statement stmt;
	
	public EditVertexView(T vertex) {
		super("Edition - " + vertex.briefData());
		this.vertex = vertex;
		C = vertex.getClass();
		nbFields = C.getDeclaredFields().length;
		
		System.out.println("Nombre d'attributs :" + nbFields);
		
		buildComposants();
		buildInterface();
		buildEvents();
		
		pack();
		setSize(400, 300);
		setVisible(true);
	}

	private void buildComposants() {
		main_panel = new JPanel(new BorderLayout());
			center_panel = new JPanel(new GridLayout(nbFields, 1, 5, 5));
				labels = new ArrayList<>();
				fields = new ArrayList<>();
				types = new ArrayList<>();
				try {
					// Construction
					BeanInfo info = Introspector.getBeanInfo(C);
					for(PropertyDescriptor pd : info.getPropertyDescriptors()) {
						if (pd.getReadMethod() != null && !"class".equals(pd.getName())) {
							// Nom de l'attribut
							String fieldName = pd.getName();
							fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
								// JLabel
								JLabel label = new JLabel(fieldName);
								labels.add(label);
							// Getter de l'attribut
							Method getter = pd.getReadMethod();
							// Type de l'attribut
							Class<?> type = pd.getPropertyType();
								types.add(type);
							Object value = getter.invoke(vertex);
								// JField
								JTextField field = new JTextField(value.toString());
								fields.add(field);
						}
					}
				} catch(Exception E) {
					E.printStackTrace();
				}
			bottom_panel = new JPanel(new BorderLayout());
		
		confirm = new JButton("Confirmer");
	}

	private void buildInterface() {
		for(int i=0 ; i<nbFields ; i++) {
			center_panel.add(labels.get(i));
			center_panel.add(fields.get(i));
		}
		
		bottom_panel.add(confirm, BorderLayout.EAST);
		
		main_panel.add(center_panel, BorderLayout.CENTER);
		main_panel.add(bottom_panel, BorderLayout.SOUTH);
		
		setContentPane(main_panel);
	}
	
	private void buildEvents() {
		confirm.addActionListener(this);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object O = e.getSource();
		if(O.getClass() == JButton.class) {
			JButton B = (JButton) O;
			if(B == confirm) {
				onConfirm();
			}
		}
	}
	
	private void onConfirm() {
		for(int i=0 ; i<nbFields ; i++) {
			String variable = labels.get(i).getText();
			variable = variable.substring(0, 1).toLowerCase() + variable.substring(1);
			// Class<?> type = types.get(i);
			// String value = fields.get(i).getText();
			try {
				BeanInfo info = Introspector.getBeanInfo(C);
				for(PropertyDescriptor pd : info.getPropertyDescriptors()) {
					if (pd.getWriteMethod() != null && !"class".equals(pd.getName())) {
						// Nom de l'attribut
						String fieldName = pd.getName();
						// Getter de l'attribut
						Method setter = pd.getWriteMethod();
						// Type de l'attribut
						Class<?> type = pd.getPropertyType();
						// Object value = getter.invoke(vertex);
						// setter.invoke(vertex, args)
						System.out.println(fieldName + " " + setter + " " + type);
					}
				}
			} catch(Exception E) {
				E.printStackTrace();
			}
		}
	}
}
