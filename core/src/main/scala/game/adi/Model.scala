package game.adi

import ai.djl.ndarray.*
import ai.djl.ndarray.types.{DataType, Shape}
import ai.djl.engine.Engine

class Model() {
    val manager = NDManager.newBaseManager()

    val w1 = manager.create(Array(
        Array(
            0.1583719104528427f,
            0.193258598446846f,
            0.14330102503299713f,
            -0.006728876382112503f,
            0.1355932503938675f,
            0.1724970042705536f,
            -0.37649497389793396f,
            0.24602016806602478f,
            0.34569427371025085f,
            0.29971155524253845f,
            0.1888352483510971f,
            0.36674484610557556f,
            0.44439750909805296f,
            0.18776951730251312f,
            -0.20592452585697174f
        )
    ))
    val w2 = manager.create(Array(
        Array(0.3023911118507385f),
        Array(0.3316458463668823f),
        Array(0.1030583381652832f),
        Array(0.13719381392002106f),
        Array(0.2115112692117691f),
        Array(0.3838488757610321f),
        Array(-0.4243799149990082f),
        Array(0.3622732162475586f),
        Array(0.37812143564224243f),
        Array(0.49619731307029724f),
        Array(0.28931382298469543f),
        Array(0.3076479732990265f),
        Array(0.42661184072494507f),
        Array(0.26110604405403137f),
        Array(-0.3899173140525818f)

    ))

    val b1 = manager.create(Array(
        0.23232685029506683f,
        0.2515452206134796f,
        0.07019311934709549f,
        -0.04002563655376434f,
        0.18818774819374084f,
        0.22179830074310303f,
        0.2982616126537323f,
        0.33477702736854553f,
        -0.05882880091667175f,
        0.2901138663291931f,
        0.2824499011039734f,
        0.1374141126871109f,
        0.061371318995952606f,
        0.2840620279312134f,
        0.4150714576244354f
    ))

    val b2 = manager.create(-0.6214818358421326f)

    val x_mean = manager.create(134.38143920898438f)
    val x_std = manager.create(59.40098190307617f)

    val y_mean = manager.create(1.1227589845657349f)
    val y_std = manager.create(0.7045105695724487f)


    private def normalise(x: NDArray): NDArray = {
        (x.sub(x_mean)).div(x_std)
    }

    def predict(mag:Float): Float = {
        var x = manager.create(Array(mag))
        x = normalise(x)
        var l1 = (x.mul(w1)).add(b1)
        l1 = l1.getNDArrayInternal.relu()
        val l2 = (l1.matMul(w2)).add(b2)

        val pred = ((l2.mul(y_std)).add(y_mean)).getFloat()
        pred
    }
    
    def close():Unit = manager.close()
}
