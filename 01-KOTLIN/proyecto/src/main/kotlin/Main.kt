import java.util.*

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
    //ejemploVariable=edadEjemplo


    //VARIABLES PRIMITIVAS
    val nombreProfesor : String = "Adrian Eguez"
    val sueldo: Double = 1.2
    val estadoCivil: Char ='C'
    val mayorEdad: Boolean =  true
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
    val coqueteo = if(esSoltero)"Si" else "No"



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
        return if(bonoEspecial == null){
            sueldo*(100/tasa)
        } else {
            sueldo*(100/tasa)+bonoEspecial
        }
    }
    calcularSueldo(10.00)
    calcularSueldo(10.00,15.00,20.00)
    calcularSueldo(10.00, bonoEspecial = 20.00) //Named Parameters
    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 15.00)//Parametros


    val sumaUno = Suma(1,1)
    val sumaDos = Suma(null, 1)
    val sumaTres = Suma(1,null)




}

abstract class NumerosJava{
    protected val numeroUno : Int
    private val numeroDos : Int

    constructor( uno:Int, dos:Int) { //Bloque de código de constructor
        this.numeroUno = uno
        this.numeroDos = dos
        println("Inicializando")
    }



}

abstract class NumerosKotlin( //Constructor primario
    //Ejemplo
    // uno: Int, (Parametro(sin modificar de acceso))
    //private var uno: Int, //Propiedad Publica Clase numeroskotlin.uno
    // var uno: Int, //Porpiedad de la clase (por defecto es public)
    //public var uno: Int
    protected val numeroUno : Int, //Propiedad de la clase protected numeroskotlin.numeroUno
    protected val numeroDos : Int //Propiedad de la clase protected numeroskotlin.numeroDos
){
    init{ //blque constructor primario
        //this.numeroUno; this.numeroDos  ; //El this es opcional, no es requerido
        numeroDos; numeroUno; //Sin el this, es lo mismo
        println("INICIALIZANDO")
    }
}

class Suma( //Constructor primario SUMA
    unoParametro:Int, //Parametro
    dosParametro: Int, //Parametro
): NumerosKotlin(unoParametro, dosParametro){ //Extendiendo y mandando los parametros (super)
    init { //Bloque codigo constructor primario
        this.numeroUno
        this.numeroDos
    }
    constructor(
        uno: Int?,
        dos: Int
    ):this(
        if (uno== null) 0 else uno,
        dos
    )

    constructor(
        uno: Int,
        dos: Int?
    ):this(
        uno,
        if (dos==null)0 else dos
    )

}