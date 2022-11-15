package lotto

import camp.nextstep.edu.missionutils.Randoms
import java.security.cert.TrustAnchor
import java.util.Objects

class Lotto(private val numbers: List<Int>) {
    private var lottoNumbers = listOf<Int>()
    private var bonusFlag: Boolean = false

    init {
        require(numbers.size == 6)
        this.lottoNumbers = numbers
        this.bonusFlag = false
    }

    // TODO: 추가 기능 구현

    override fun toString(): String {
        // function to make class Lotto to String
        // - to print out -
        var string = "["
        this.numbers
        for (i in 0 until 5){
            string += this.numbers[i].toString() + ", "
        }

        return string + this.numbers[5].toString() + "]"
    }

    private fun printOneLotto(){
        println(this.toString())
    }

    private fun checkLottery(winlottonum : Lotto, bonusnum: Int) : Int {
        var lottery: Int = 0
        var bonusLottery = false

        val winnum = winlottonum.lottoNumbers
        val checknum = this.lottoNumbers

        winnum.forEach() {it
            lottery += compareWithList(checknum, it)
        }
        if (lottery == 5){
            this.bonusFlag = true
        }
        if(this.bonusFlag){
            bonusLottery = bonusNumCheck(bonusnum)
        }
        return lottery
    }

    private fun compareWithList(checkList: List<Int>, win: Int): Int{
        if (checkList.contains(win)){
            return 1
        }
        else
            return 0
    }

    private fun bonusNumCheck(bonusnum : Int) : Boolean{
        // checks for bonus number
        val checknum = this.lottoNumbers
        var lottery = 0
        checknum.forEach() {it
            lottery += compareWithList(checknum, it)
        }
        return lottery>=1
    }


}



fun buyLotto(){
    println("구매 금액을 입력해 주세요.")
    var userInput : Int? = readLine()!!.toIntOrNull()
    userInput = userInput?.div(1000)
    userInputCheck(userInput)


    var lottoList = createLottoList(userInput!!)
    println("\n${userInput}개를 구매했습니다.\n")
    printLottos(lottoList)
}

fun winningNumber(){
    println("당첨 번호를 입력해 주세요.")
    var winstr : List<String> = readLine()!!.split(",")
    var winint : List<Int>   = listOf()
    winningNumCheck(winint)
    winstr.forEach() {
        winint += it.toInt()
    }

    println("\n보너스 번호를 입력해 주세요.")
    var bonus : Int? = readLine()!!.toIntOrNull()
    bonusNumCheck(bonus)
    
    result(winint, bonus!!)
}

fun result(winint: List<Int>, bonus: Int) {

}

fun bonusNumCheck(bonus: Int?) {
    val E = ExceptionExplain.WrongBonusNum

    if (bonus !is Int)
        E.checkAndThrowException()

    checkRangeOfNum(bonus!!)
}

fun winningNumCheck(winint: List<Int>) {
    val E = ExceptionExplain.WrongWinNum

    if (winint.equals(null))
        E.checkAndThrowException()
    if (winint !is List<Int>)
        E.checkAndThrowException()

    winint.forEach(){
        checkRangeOfNum(it)
    }
}

fun checkRangeOfNum(it: Int) {
    val E = ExceptionExplain.WrongWinNum

    if (it >45 || it<1)
        E.checkAndThrowException()
}

fun printLottos(lottoList: List<Lotto>){
    lottoList.forEach(){
        println(it.toString())
    }
}

fun userInputCheck(userInput: Int?){
    val E = ExceptionExplain.WrongPayment

    if (1 > userInput!!)
        E.checkAndThrowException()
    if (userInput == null)
        E.checkAndThrowException()
    if (userInput !is Int)
        E.checkAndThrowException()

}

fun createOneLotto() : Lotto {
    // 새로운 랜덤한 로또 용지 하나를 만드는 함수

    var newLotto = mutableSetOf<Int>()
    while (newLotto.size < 6)
        newLotto+= Randoms.pickNumberInRange(1, 45)
    return ListToLotto(newLotto.toList())
}

fun ListToLotto(lottoInList: List<Int>) : Lotto {
    return Lotto(lottoInList)
}

fun createLottoList(howMany : Int) : List<Lotto> {
    // 로또 용지의 전체 리스트를 리턴함
    var newLottoList: List<Lotto> = listOf()

    for (i: Int in 0 until howMany) {
        val aLotto = createOneLotto()
        newLottoList += aLotto
    }
    return newLottoList
}

enum class ExceptionExplain{
    WrongPayment{
        override fun checkAndThrowException() {
            throw IllegalArgumentException("[ERROR] 입력 금액은 1000원 단위의 정수입니다.")
        }
    }, WrongWinNum{
        override fun checkAndThrowException() {
            throw IllegalArgumentException("[ERROR] 당첨 번호는 1이상 45이하입니다.")
        }
    }, WrongBonusNum{
        override fun checkAndThrowException() {
            throw IllegalArgumentException("[ERROR] 당첨 번호는 1이상 45이하입니다.")
        }
    };
    abstract fun checkAndThrowException()
}
