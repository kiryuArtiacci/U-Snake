instalen javaFX antes de ponerse a correr vainas cuando este programa este adelantado, cuerda de mamaguebos 


aparte de instalar todos los .jar de la libreria javaFX deben poner esta linea en el launch.json de su proyecto

"vmArgs": "--module-path direccion de los archivos de javafx --add-modules javafx.controls,javafx.fxml",
