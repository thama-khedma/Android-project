package fragments

import android.os.Bundle
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.example.khedma.R
import kotlin.Int
import kotlin.String


public class EntrepriseDetailsFragmentDirections private constructor() {
  private data class ActionRestaurantDetailsFragmentToEditRestaurantFragment(
    public val restaurantId: String
  ) : NavDirections {
    public override val actionId: Int =
        R.id.action_restaurantDetailsFragment_to_editRestaurantFragment

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("restaurantId", this.restaurantId)
        return result
      }
  }

  private data class ActionRestaurantDetailsFragmentToRestaurantMenuFragment(
    public val restaurantId: String
  ) : NavDirections {
    public override val actionId: Int =
        R.id.action_restaurantDetailsFragment_to_restaurantMenuFragment

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("restaurantId", this.restaurantId)
        return result
      }
  }

  public companion object {
    public fun actionRestaurantDetailsFragmentToEditRestaurantFragment(restaurantId: String):
        NavDirections = ActionRestaurantDetailsFragmentToEditRestaurantFragment(restaurantId)

    public fun actionRestaurantDetailsFragmentToMyRestaurantsFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_restaurantDetailsFragment_to_myRestaurantsFragment)

    public fun actionRestaurantDetailsFragmentToRestaurantMenuFragment(restaurantId: String):
        NavDirections = ActionRestaurantDetailsFragmentToRestaurantMenuFragment(restaurantId)
  }
}
