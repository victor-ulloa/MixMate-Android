package com.example.mixmate.ui.home

import androidx.lifecycle.ViewModel
import com.example.mixmate.data.Cocktail
import com.example.mixmate.repository.Supabase

class HomeViewModel : ViewModel() {
    val supabase = Supabase

    val allCocktailsLocal : Array<Cocktail> = arrayOf(
        Cocktail(2, "Frozen Margarita","Frozen Margarita Description","Frozen Margarita", "https://images.pexels.com/photos/7809757/pexels-photo-7809757.jpeg"),
        Cocktail(1, "Whiskey Sour","Whiskey Sour Description","Whiskey Sour", "https://images.pexels.com/photos/6542662/pexels-photo-6542662.jpeg"),
        Cocktail(3, "Vodka Martini","Vodka Martini Description","Vodka Martini", "https://images.pexels.com/photos/4786625/pexels-photo-4786625.jpeg"),
        Cocktail(4, "Mojito","Mojito Description","Mojito", "https://images.pexels.com/photos/4021983/pexels-photo-4021983.jpeg"),
        Cocktail(5, "Old-fashioned","Old-fashioned Description","Old-fashioned", "https://images.pexels.com/photos/19252758/pexels-photo-19252758/free-photo-of-whiskey-with-ice-cubes-and-orange-peel-in-lowball-glass.jpeg"),
        Cocktail(6, "Espresso Martini", "Espresso Martini Description","Espresso Martini", "https://images.pexels.com/photos/15082311/pexels-photo-15082311/free-photo-of-glass-of-espresso-martini-cocktail.jpeg" ),
        Cocktail(7, "Manhattan", "Manhattan Description", "Manhattan", "https://images.pexels.com/photos/7809770/pexels-photo-7809770.jpeg"),
        Cocktail(8, "Piña Colada", "Piña Colada Description", "Piña Colada", "https://images.pexels.com/photos/10986577/pexels-photo-10986577.jpeg"),
        Cocktail(9, "Mango Mojito", "Mango Mojito Description", "Mango Mojito", "https://images.pexels.com/photos/10836605/pexels-photo-10836605.jpeg"),
        Cocktail(10, "Jalapeno Vodka", "Jalapeno Vodka Description","Jalapeno Vodka","https://images.pexels.com/photos/4631022/pexels-photo-4631022.jpeg"),
        Cocktail(11,"Polish Honey Vodka", "Polish Honey Vodka Description", "Polish Honey Vodka", "https://images.pexels.com/photos/4631019/pexels-photo-4631019.jpeg")
    )
}