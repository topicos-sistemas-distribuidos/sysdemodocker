package net.sysarm.sysdemo.util;

import java.nio.file.FileSystems;

public class Constantes {
	public static final String USER_DIR = "user.dir";
	public static final int ITENS_POR_PAGINA=10;
	public static final String UPLOAD_DIRECTORY = System.getProperty(USER_DIR) + FileSystems.getDefault().getSeparator()+ "uploads";
	public static final String UPLOAD_USER_DIRECTORY = System.getProperty(USER_DIR) + FileSystems.getDefault().getSeparator() + "users";
	public static final String PICTURES_DIRECTORY = System.getProperty(USER_DIR) + FileSystems.getDefault().getSeparator() + "uploads" + FileSystems.getDefault().getSeparator() + "pictures";
	public static final String MAIN_PATH = System.getProperty(USER_DIR);
}
