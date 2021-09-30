package com.example.ejercicio01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    lateinit var test: String // Se inicia de forma tardia y no puede ser nunca nulo, antes de usarla hay que inicianizarlo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val teacher = Teacher("Pepe","Juan",43,null,true) //instanciar un objeto de clase Teacher
        val teacher2 = Teacher("Pepe","Juan",43,null,true) //instanciar un objeto de clase Teacher
        val sum = teacher.classroomManage?.length // esto es basicamente indicarle a programa que eso puede ser o no nulo
        val sum2 = teacher.edad +  teacher2.edad ;
        val sumchar = teacher.classroomManage!!.length + teacher2.classroomManage!!.length  //Esto no se puede usar, prohivido si el valor es nulo va a dar un error

        test = "hola" //Inicializar variable tardia
        teacher.classroomManage?.let { classroomManagenotnull ->
            teacher2.classroomManage?.let {  classroomManage2notnull ->
                if(this::test.isInitialized){//los dos puntos significan variable de la misma instancia este codigo es para ver si esta inicializado la variable tardia
                    val sumcharSize = classroomManagenotnull.length + classroomManage2notnull.length + test.length
                    Log.e("Android prueba","SumCharSize=$sumcharSize") //lo que vamos a usar en vez del println
                }
            }
        } //opccion correcta de hacer esto es un si no eres nulo haz esto, esto -> classroomManagenotnull esto es un cambio de nombre y -> significa lo que va a hacer si no es null mete el valor si no es nulo en la variable detras del {
        val person = Person("","",20)
        val person2 = Student("","",20)
        //no se pueden castear las clases que no estan heredando solo se pueden las que heredan
        (person as Teacher).isManager //Castear de un objeto a otro
        (person2 as? Teacher)?.isManager //Esto es para comprobar si hereda o no de la clase student

        val list = mutableListOf<Animal>() //creamo una lista de animales y metemos diferentes tipos
        list.add(Cat("",2))
        list.add(Rabbit("",2))
        list.add(Dog("",2))
        list.add(Dog("",2))

        list.forEach{ item ->
            (item as? IMakeSound)?.makeNoise() //esto es un bucle for eahc cada elemto se va a meter en la variable item y va a hacer lo de dentro y si ese item es decir animal tiene implementada la interfaz va a ejecutar ese metodo
        }
    }
}