fun main(args: Array<String>){

    println("Hello word")

    //INMUTABLES (NO se reasignar "=")
    val inmutable: String = "Adrian"
    //MUTABLES (Pueden ser reasignadas)
    var mutable: String = "Vicente"
    mutable = "Adrian"

    //dUCK tYPING
    var ejemploVariable = "Emily Montalvo"
    val edadEjemplo: Int = 12
    ejemploVariable.trim()
    ejemploVariable=edadEjemplo


    //VARIABLES PRIMITIVAS
    val nombreProfesor : String = "Adrian Eguez"
    val sueldo: Double = 1.2
    val estadoCivil: Char = "C"
    val mayorEdad: Boolen =  true
    //Clases Java
    val fechaNacimiento : Date = Date()

    //SWITCH
    val estadoCivilWhen = "C"
    when(estadoCivilWhen){
        ("C") -> {
            println("Soltero")
        }
        else -> {
            println("No sabemos")
        }
    }

    val esSoltero = (estadoCivilWhen == "S")
    val coqueteo = if(esSoltero)"Si" else"No"



    //void -> unit
    fun imprimirNombre(nombre:String):Unit{
        println("Nombre:  ${nombre}") //Template Strings
        //"Nombre:"+nombre
    }

    fun calcularSueldo (
        sueldo:Double, //requerido
        tasa: Double = 12.00, //Opdcional (Por defecto)
        bonoEspecial: Double? = null, //Opcion null -> Puede ser nulo en alguno momento null
    ): Double{
    //Int -> Int?(nullable)
        //String -> String?(nullable)
        //Date -> Date?(nullable)
        if(bonoEspecial == null){
            return sueldo*(100/tasa)
        } else {
            return sueldo*(100/tasa)+bonoEspecial
        }
    }


    calcularSueldo(10.00)
    calcularSueldo(10.00,15.00,20.00)
    calcularSueldo(10.00, bonoEspecial = 20.00) //Named Parameters
    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 15.00)//Parametros







}