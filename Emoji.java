package server;

import java.awt.List;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

public class Emoji extends JComboBox{
	 public Emoji()
	 {
		 super();
		 super.addItem("");
		 super.addItem("😀");
		 super.addItem("😃");
		 super.addItem("😄");
		 super.addItem("😁");
		 super.addItem("😆");
		 super.addItem("😅");
		 super.addItem("🤣");
		 super.addItem("😂");
		 super.addItem("🙂");
		 super.addItem("🙃");
		 super.addItem("😉");
	 }
}
