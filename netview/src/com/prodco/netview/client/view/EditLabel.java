package com.prodco.netview.client.view;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FocusListenerAdapter;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.KeyboardListenerAdapter;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EditLabel extends SimplePanel {
	
	private Label label = new Label();
	private TextBox edit;
	private ChangeListener changeListener;
	
	public EditLabel(){
		this( "" );
	}
	
	public EditLabel( String text ){
		setWidget( label );
		label.setText( text );
		DOM.setStyleAttribute( label.getElement(), "cursor", "hand" );
		label.setWidth("100%");
		label.setWordWrap(true);
		label.addClickListener( new ClickListener(){ 
			public void onClick( Widget sender ){
				edit();
			}
		});
		
	}
	
	private void ensureEditBoxLoaded(){
		if( edit == null ){
			edit = new TextBox();
			edit.setWidth("100%");
			edit.addKeyboardListener( new KeyboardListenerAdapter(){
				public void onKeyPress(Widget sender, char keyCode, int modifiers){
					if( keyCode == KeyboardListener.KEY_ENTER )
						commitChanges();
					else if( keyCode == KeyboardListener.KEY_ESCAPE )
						setWidget(label);
				}
			});

			edit.addFocusListener( new FocusListenerAdapter(){
				public void onLostFocus(Widget sender){
					commitChanges();
				}
			});
		}
	}
	
	private void commitChanges(){
		String newText = edit.getText();
		if( newText.compareTo(label.getText()) != 0 ){
			label.setText( newText );
			if( changeListener != null ){
				changeListener.onChange(this);
			}
		}
		setWidget(label);
	}
	
	public void edit(){
		ensureEditBoxLoaded();
		setWidget(edit);
		edit.setText( label.getText() );
		edit.selectAll();
		edit.setFocus( true );
	}
	
	public void setChangeListener( ChangeListener changeListener ){
		this.changeListener = changeListener;
	}
	
	public void setText( String text ){
		label.setText( text );
	}
	
	public String getText(){
		return label.getText();
	}
}
