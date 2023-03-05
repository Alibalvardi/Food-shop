package com.example.alifood

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alifood.databinding.ActivityMainBinding
import com.example.alifood.databinding.AddNewfoodBinding
import com.example.alifood.databinding.DeleteItemBinding
import com.example.alifood.databinding.EditItemBinding


class MainActivity : AppCompatActivity(), FoodAdaptor.FoodEvents {
    lateinit var binding: ActivityMainBinding
    private lateinit var foodAdaptor: FoodAdaptor
    private val foodList = arrayListOf(
        Food(
            "Hamburger",
            "15",
            "3",
            "Isfahan, Iran",
            "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food1.jpg",
            20,
            4.5f
        ),
        Food(
            "Grilled fish",
            "20",
            "2.1",
            "Tehran, Iran",
            "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food2.jpg",
            10,
            4f
        ),
        Food(
            "Lasania",
            "40",
            "1.4",
            "Isfahan, Iran",
            "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food3.jpg",
            30,
            2f
        ),
        Food(
            "pizza",
            "10",
            "2.5",
            "Zahedan, Iran",
            "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food4.jpg",
            80,
            1.5f
        ),
        Food(
            "Sushi",
            "20",
            "3.2",
            "Mashhad, Iran",
            "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food5.jpg",
            200,
            3f
        ),
        Food(
            "Roasted Fish",
            "40",
            "3.7",
            "Jolfa, Iran",
            "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food6.jpg",
            50,
            3.5f
        ),
        Food(
            "Fried chicken",
            "70",
            "3.5",
            "NewYork, USA",
            "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food7.jpg",
            70,
            2.5f
        ),
        Food(
            "Vegetable salad",
            "12",
            "3.6",
            "Berlin, Germany",
            "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food8.jpg",
            40,
            4.5f
        ),
        Food(
            "Grilled chicken",
            "10",
            "3.7",
            "Beijing, China",
            "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food9.jpg",
            15,
            5f
        ),
        Food(
            "Baryooni",
            "16",
            "10",
            "Ilam, Iran",
            "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food10.jpg",
            28,
            4.5f
        ),
        Food(
            "Ghorme Sabzi",
            "11.5",
            "7.5",
            "Karaj, Iran",
            "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food11.jpg",
            27,
            5f
        ),
        Food(
            "Rice",
            "12.5",
            "2.4",
            "Shiraz, Iran",
            "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food12.jpg",
            35,
            2.5f
        ),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        foodAdaptor = FoodAdaptor(foodList.clone() as ArrayList<Food>, this)
        binding.recycleMain.adapter = foodAdaptor
        binding.recycleMain.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)


        binding.btnAddNewFood.setOnClickListener {

            val dialog = AlertDialog.Builder(this).create()

            val dialogBinding = AddNewfoodBinding.inflate(layoutInflater)

            dialog.setView(dialogBinding.root)
            dialog.setCancelable(true)
            dialog.show()


            dialogBinding.btnDone.setOnClickListener {

                if (dialogBinding.edtName.length() > 0 &&
                    dialogBinding.edtCity.length() > 0 &&
                    dialogBinding.edtPrice.length() > 0 &&
                    dialogBinding.edtDistance.length() > 0
                ) {
                    val name = dialogBinding.edtName.text.toString()
                    val city = dialogBinding.edtCity.text.toString()
                    val price = dialogBinding.edtPrice.text.toString()
                    val distance = dialogBinding.edtDistance.text.toString()

                    val newFood = Food(name, price, distance, city, "", 0, 0f)

                    foodAdaptor.addFood(newFood)


                    dialog.dismiss()
                    binding.recycleMain.scrollToPosition(0)
                } else {
                    Toast.makeText(this, "Please enter all of text", Toast.LENGTH_SHORT).show()
                }

            }
        }


        binding.edtSearch.addTextChangedListener { edtText ->

            if (edtText!!.isNotEmpty()) {
                val cloneList = foodList.clone() as ArrayList<Food>
                val filteredList = cloneList.filter { food ->
                    food.textName.contains(edtText)
                }
                foodAdaptor.setData(ArrayList(filteredList))

            } else {
                foodAdaptor.setData(foodList.clone() as ArrayList<Food>)
            }

        }

    }

    override fun foodClicked(food: Food, adapterPosition: Int) {

        val dialog = AlertDialog.Builder(this).create()

        val editBinding = EditItemBinding.inflate(layoutInflater)
        editBinding.edtName.setText(food.textName)
        editBinding.edtCity.setText(food.textCity)
        editBinding.edtPrice.setText(food.textPrice)
        editBinding.edtDistance.setText(food.textDistance)

        dialog.setView(editBinding.root)
        dialog.setCancelable(true)
        dialog.show()

        editBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        editBinding.btnDone.setOnClickListener {

            if (editBinding.edtName.length() > 0 &&
                editBinding.edtCity.length() > 0 &&
                editBinding.edtPrice.length() > 0 &&
                editBinding.edtDistance.length() > 0
            ) {
                val name = editBinding.edtName.text.toString()
                val city = editBinding.edtCity.text.toString()
                val price = editBinding.edtPrice.text.toString()
                val distance = editBinding.edtDistance.text.toString()

                val newFood =
                    Food(name, price, distance, city, food.urlImg, food.numOfRating, food.ratingBar)

                foodAdaptor.updateFood(newFood, adapterPosition)

                dialog.dismiss()

            } else {
                Toast.makeText(this, "Please enter all of text", Toast.LENGTH_SHORT).show()
            }

        }

    }

    override fun longFoodClicked(food: Food, adapterPosition: Int) {

        val dialog = AlertDialog.Builder(this).create()
        val bindingDelete = DeleteItemBinding.inflate(layoutInflater)
        dialog.setView(bindingDelete.root)
        dialog.setCancelable(true)
        dialog.show()

        bindingDelete.btnDelete.setOnClickListener {
            dialog.dismiss()
            foodAdaptor.removeFood(food, adapterPosition)
        }

        bindingDelete.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

    }


}