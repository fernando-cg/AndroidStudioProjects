package es.fesac.practica.game

import es.fesac.practica.ui.model.game.AnimationTypeEnum

class AnimationGrid (numSquaresX: Int, numSquaresY: Int) {
    var field : Array<Array<ArrayList<AnimationCell>?>> = arrayOf()

    var activeAnimations = 0

    init {

        field = Array(numSquaresX) {
            arrayOfNulls(numSquaresY)
        }

        for (xPosition in 0 until numSquaresX){
            for (yPosition in 0 until numSquaresY){
                field[xPosition][yPosition] = arrayListOf()
            }
        }
    }

    fun cancelAnimations(){
        field.forEach { x ->
            x.forEach { y ->
                y?.clear()
            }
        }
        activeAnimations = 0
    }

    fun startAnimation(x: Int, y: Int, animationType: AnimationTypeEnum, animationTime: Long, delayTime: Long, extras: IntArray?){
        val animationToAdd = AnimationCell(x, y, animationType, animationTime, delayTime, extras)
        field[x][y]?.add(animationToAdd)
        activeAnimations++
    }
    fun isAnimationActive():Boolean{
        return activeAnimations != 0
    }

    fun startFinishingAnimation(type : AnimationTypeEnum, animationTime: Long, delayTime: Long) {
        cancelAnimations()
        field.forEachIndexed { indexX, x ->
            x.forEachIndexed { indexY, y ->
                field[indexX][indexY]?.add(AnimationCell(indexX, indexY, type, animationTime, delayTime, null))
                activeAnimations++
            }
        }
    }

    fun getAnimationCellList(positionX: Int, positionY: Int): ArrayList<AnimationCell>? {


        return field[positionX][positionY]
    }

    fun tickAll(elapsedTime: Long) {
        val cancelledAnimations = arrayListOf<AnimationCell>()
        field.forEach { x ->
            x.forEach { y ->
                y?.forEach{ animation ->
                    tickAnimation(animation, elapsedTime, cancelledAnimations)
                }
            }
        }
        cancelledAnimations.forEach { animation ->
            cancelAnimation(animation)
        }
    }

    fun cancelAnimation(animation: AnimationCell) {
        field[animation.x][animation.y]?.remove(animation)
    }

    fun tickAnimation(animation: AnimationCell, elapsedTime: Long, cancelledAnimations: ArrayList<AnimationCell>){
        animation.tick(elapsedTime)
        if (animation.animationDone()){
            cancelledAnimations.add(animation)
            activeAnimations--
        }
    }
}
