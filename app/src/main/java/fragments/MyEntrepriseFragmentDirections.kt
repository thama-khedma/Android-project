package fragments

import android.os.Bundle
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.example.khedma.R
import kotlin.Int
import kotlin.String


public class MyEntrepriseFragmentDirections private constructor() {
  private data class ActionMyRestaurantsFragmentToRestaurantDetailsFragment(
    public val restaurantId: String
  ) : NavDirections {
    public override val actionId: Int =
        R.id.action_myRestaurantsFragment_to_restaurantDetailsFragment

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("restaurantId", this.restaurantId)
        return result
      }
  }

  public companion object {
    public fun actionMyRestaurantsFragmentToAddRestaurantFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_myRestaurantsFragment_to_addRestaurantFragment)

    public fun actionMyRestaurantsFragmentToRestaurantDetailsFragment(restaurantId: String):
        NavDirections = ActionMyRestaurantsFragmentToRestaurantDetailsFragment(restaurantId)
  }


}
