package cdbol.br.com.clubedabola.screens.becomeplayer.size


import `in`.galaxyofandroid.spinerdialog.SpinnerDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cdbol.br.com.clubedabola.R
import cdbol.br.com.clubedabola.screens.becomeplayer.BecomePlayerContract
import kotlinx.android.synthetic.main.fragment_goalkeep_size.*


class GoalkeeperSizeFragment : Fragment(), BecomePlayerContract.PageView {


    val genderList = arrayListOf<String>()
    val gloveSizeList = arrayListOf<String>()
    val tshirtSizeList = arrayListOf<String>()

    var genderPicker: SpinnerDialog? = null
    var glovePicker: SpinnerDialog? = null
    var tshirtPicker: SpinnerDialog? = null
    var mainView: BecomePlayerContract.View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        return inflater.inflate(R.layout.fragment_goalkeep_size, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainView = activity as BecomePlayerContract.View
        initGenderPicker()
        initGlovePicker()
        initTshirtPicker()

    }

    override fun enableNextPosition() {
        if (tv_gender_selected.text.isNotEmpty() && tv_select_size.text.isNotEmpty() && tv_select_tshirt.text.isNotEmpty()){
            mainView?.onNext(true)
        }else{
            mainView?.onNext(false)
        }

    }

    fun initGenderPicker(){

        genderList.add("Masculino")
        genderList.add("Feminino")

        genderPicker = SpinnerDialog(activity, genderList, "Selecione o gênero", R.style.DialogAnimations_SmileWindow, "Fechar")

        container_gender.setOnClickListener({
            genderPicker!!.showSpinerDialog()
        })

        genderPicker!!.bindOnSpinerListener { item, _ ->
            tv_gender_selected.text = item
            mainView!!.mainPresenter().saveGender(item)
            enableNextPosition()
        }

    }

    fun initGlovePicker(){

        gloveSizeList.add("7")
        gloveSizeList.add("8")
        gloveSizeList.add("9")
        gloveSizeList.add("10")
        gloveSizeList.add("11")
        gloveSizeList.add("12")

        glovePicker = SpinnerDialog(activity, gloveSizeList, "Selecione o tamanho da luva", R.style.DialogAnimations_SmileWindow, "Fechar")

        container_size.setOnClickListener({
            glovePicker!!.showSpinerDialog()
        })

        glovePicker!!.bindOnSpinerListener { item, _ ->
            tv_select_size.text = item
            mainView!!.mainPresenter().saveGloveSize(item)
            enableNextPosition()
        }

    }

    fun initTshirtPicker(){

        tshirtSizeList.add("P")
        tshirtSizeList.add("M")
        tshirtSizeList.add("G")
        tshirtSizeList.add("GG")

        tshirtPicker = SpinnerDialog(activity, tshirtSizeList, "Selecione o tamanho da camimsa", R.style.DialogAnimations_SmileWindow, "Fechar")


        container_tshirt.setOnClickListener({
            tshirtPicker!!.showSpinerDialog()
        })

        tshirtPicker!!.bindOnSpinerListener { item, _ ->
            tv_select_tshirt.text = item
            mainView!!.mainPresenter().saveTshirtSize(item)
            enableNextPosition()
        }

    }

}
