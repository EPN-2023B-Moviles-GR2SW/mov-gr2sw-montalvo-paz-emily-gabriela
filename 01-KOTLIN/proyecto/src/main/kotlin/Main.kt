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
    val sumaCuatro = Suma(null,null)
    sumaUno.sumar()
    sumaDos.sumar()
    sumaTres.sumar()
    sumaCuatro.sumar()

    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)

    val arregloEstatico : Array<Int> = arrayOf<Int>(1,2,3)
    println(arregloEstatico)

    val arregloDinamico : ArrayList<Int> = arrayListOf<Int>(1,2,3,4,5,6,7,8,9,10)
    println(arregloDinamico)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico)

    //FOR EACH -> Unit
    //Iterar un arreglo
    val respuestaForEach: Unit = arregloDinamico.forEach { valorActual: Int ->
        println("Valor actual: ${valorActual}")
    }

    arregloDinamico.forEach{ println("Valor actual: ${it}") } //Esto solo sirve cuando tengo un parametro

    arregloEstatico.forEachIndexed { index:Int, valorActual:Int ->
        println("Valor ${valorActual} - Indice: ${index}")
    }

    println(respuestaForEach)

    //MAP -> Muta el arreglo (cambia el arreglo)
    //1. Envirmos el nuevo valos de la iteracion
    //2. Nos devuelve un NUEVO ARREGLO con los valores modificados

    val respuestaMap : List<Double> = arregloDinamico.map {
        valorActual:Int -> return@map valorActual.toDouble()+100.00
    }
    println(respuestaMap)
    val respuestaMapDos = arregloDinamico.map{it+15}
    println(respuestaMapDos)
    //Filter ->  FILTRAR EL ARREGLO
    //1. DEVOLVER una expresion TRUE or FALSE
    //2. Nuevo arreglo filtrado

    val respuestFilter : List<Int> = arregloDinamico.filter {
        valorActual: Int -> val mayoresACinco: Boolean = valorActual > 5
        return@filter mayoresACinco
    }
    println(respuestFilter)
    val respuestFilterDos = arregloDinamico.filter { it<=5 }
    println(respuestFilterDos)


}

// CLASES

abstract class NumerosJava{  //Como seria una clase en Java
    protected val numeroUno : Int
    private val numeroDos : Int

    constructor( uno:Int, dos:Int) { //Bloque de código de constructor
        this.numeroUno = uno
        this.numeroDos = dos
        println("Inicializando")
    }
}


abstract class NumerosKotlin( //Clase en Kotlin //Constructor primario
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
    constructor( uno: Int?, dos: Int ):this(
        if (uno== null) 0 else uno,
        dos
    )
    constructor( uno: Int, dos: Int? ):this(
        uno,
        if (dos == null) 0 else dos
    )
    constructor( uno: Int?, dos: Int? ):this(
        if (uno == null) 0 else uno,
        if (dos == null) 0 else uno
    )

    //Funciones de clase Suma

    // public por defecto, o usar private o protected
    public fun sumar (): Int {
        val total = numeroUno + numeroDos
        agregarHistorial(total)
        return  total
    }

    // Atributos y metodos "Compartidos"

    companion object {  //Entre las instancias
        val pi = 3.14

        fun elevarAlCuadrado(num:Int):Int{
            return num * num
        }

        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(valorNuevaSuma: Int){
            historialSumas.add(valorNuevaSuma)
        }
    }

}