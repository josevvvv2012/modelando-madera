package ide;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public  class DEF {

	public static final int galeria = -1;
	public static final int punto = 1;
	public static final int linea = 2;
	public static final int polilinea = 3;
	public static final int circulo = 4;
	public static final int elipse = 5;
	public static final int bezier = 6;
	
	public static final int retardoPto= 40;
	public static final int retardoInicial= 15999;
	public static final int retardoLevantar= 1500;
	
	public static final int vistaYX = 1;	
	public static final int vistaZY = 2;
	public static final int vistaZX = 3;
	public static final int vistaPerspectiva = 4;
	
	
	
	public static final String vistaYXs = "Arriba";	
	public static final String vistaZYs = "Lateral";
	public static final String vistaZXs = "Frente";
	public static final String vistaPerspectivas = "Perspectiva";
	
	public static final Color frenteColor= new Color(Display.getCurrent(), 255, 255,255);
	public static final Color fondoColor = new Color(Display.getCurrent(), 0, 0,0);
	public static final Color colorSelec= new Color(Display.getCurrent(), 0, 0,255);
	
	public static final double incRobot = 0.12;
	public static final int arriba = 430000;
	
	public static final String ext = ".xml";
	public static final String extpgm = ".pgm";
	public static final String mArchivo = "Archivo";
	public static final String mArchivoNuevo = "Nuevo";
	public static final String mArchivoAbrir = "Abrir";
	public static final String mArchivoImportar = "Importar";
	public static final String mArchivoCerrar = "Cerrar";
	public static final String mArchivoGuardar = "Guardar";
	public static final String mArchivoGuardarComo = "Guardar Como";
	public static final String mArchivoSalir = "Salir";
	
	public static final String mEdicion = "Edición";
	public static final String mEdicionDeshacer = "Deshacer";
	public static final String mEdicionRehacer= "Rehacer";
	public static final String mEdicionEliminar= "Eliminar primitiva";
	public static final String mEdicionDuplicar= "Duplicar primitiva";
	public static final String mEdicionConfiguracion= "Preferencias del sistema";

	public static final String mHerramientas = "Herramienta";
	public static final String mHerramientasActivarRobot = "Activar Robot";
	public static final String mHerramientasRobotizar = "Iniciar Proceso Robot";
	
	public static final String mAyuda = "Ayuda";
	public static final String mAyudaAcercaDe = "Acerca de...";
	
	public static final String pCerrar = "¿Desea cerrar el Proyecto actual?";
	public static final String pSalir = "¿Desea salir del programa?";
	
	public static final String tPrimitivas = "Herramientas de diseño";
	public static final String tPrimitivasBasicas = "Prim. Básicas";
	public static final String tGaleria = "Galeria";
	public static final String tBiblioteca = "Biblioteca";
	public static final String tFigurasProyecto = "Figuras en proyecto";
	public static final String tEnviaRobot = "Envia a Robot";
	public static final String tPreviewOpengl = "Preview OpenGL";
	
	public static final String tVistasDiseno = "Vistas Diseño";
	
	public static final String error = "Error";
	public static final String errorConexionPuerto = "Error al intentar conectar el puerto\n";
	public static final String errorNoConexion = "No hay conexion, intente activar el puerto";
	public static final String errorEnviando = "Existe un envio en curso, ¿Desea cancelarlo?";
	public static final String errorNoFiguraSel = "No ha seleccionado una figura, verifique por favor";
	public static final String errorFormatoConv = "Formato incorrecto de entrada, verifique por favor";
	public static final String robotFinalizado = "Se ha completado el envio satisfactoriamente";
	public static final String robotMSG = "Robot";
	
	
	public static final String lProfundidad = "Profundidad:";
	public static final String lFiguras = "Figuras";
	public static final String lPunto = "Punto";
	public static final String lValor = "Valor";
	public static final String lRobotEnviando = "Enviando a robot SCARA";
	public static final String lRobotFigura = "Figura";
	public static final String lRobotDe = "de";
	public static final String lRobotPunto = "Punto";
	
	
	public static final String pPunto = "         Punto";
	public static final String pLinea = "        Linea";
	public static final String pPolilinea = "       Polilinea";
	public static final String pCirculo = "       Circulo";
	public static final String pElipse = "    Elipse";
	public static final String pBezier = "      Bezier";
	
	public static final String bEliminar = "Eliminar";
	public static final String bDuplicar = "Duplicar";
	public static final String bEscalar = "Escalar";
	public static final String bRotar = "Rotar";
	public static final String bFijar = "Fijar";
	public static final String bReflejar = "Reflejar";
	public static final String bCancelar= "Cancelar";
	public static final String bIniciar= "Iniciar";
	public static final String bCerrar= "Cerrar";
	public static final String bUp = "/\\";
	public static final String bDown = "\\/";
	public static final String bIzq = "<";
	public static final String bDer= ">";
	public static final String bEnviaRobot= "Enviar ...";
	public static final String bDetieneRobot= "Detener ...";
	
	public static final int oInsertar=1;
	public static final int oEliminar=2;
	public static final int oTrasladar=3;
	public static final int oEscalar=4;
	public static final int oRotar=5;
	public static final int oFijar=6;
	
	
	public static final void mostrarMSG(String str, String title,Shell sShell ) {
		MessageBox messageBox = new MessageBox(sShell, SWT.ICON_INFORMATION
	            | SWT.OK);
	        messageBox.setMessage(str);
	        messageBox.setText(title);
	        messageBox.open();	        
	}
	
	public static final int pedirConfirmacion(String str, String title, Shell sShell) {
		MessageBox messageBox = new MessageBox(sShell, SWT.ICON_QUESTION
	            | SWT.YES | SWT.NO);
	        messageBox.setMessage(str);
	        messageBox.setText(title);
	        return messageBox.open();	        
	}
}
