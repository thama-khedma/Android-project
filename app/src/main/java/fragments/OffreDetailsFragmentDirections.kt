package fragments

import android.os.Bundle
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.example.khedma.R
import kotlin.Int
import kotlin.String


public class OffreDetailsFragmentDirections private constructor() {
  private data class ActionOffreDetailsFragmentToEditOffreFragment(
    public val offreId: String
  ) : NavDirections {
    public override val actionId: Int =
        R.id.action_offerDetailsFragment_to_editOfferFragment

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("offreId", this.offreId)
        return result
      }
  }

  private data class ActionOffreDetailsFragmentToOffreMenuFragment(
    public val offreId: String
  ) : NavDirections {
    public override val actionId: Int =
        R.id.action_offerDetailsFragment_to_myOffersFragment

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("offreId", this.offreId)
        return result
      }
  }

  public companion object {
    public fun actionOffreDetailsFragmentToEditOffreFragment(offreId: String):
        NavDirections = ActionOffreDetailsFragmentToEditOffreFragment(offreId)

    public fun actionOffreDetailsFragmentToMyOffresFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_offerDetailsFragment_to_myOffersFragment)

    public fun actionOffreDetailsFragmentToOffreMenuFragment(offreId: String):
        NavDirections = ActionOffreDetailsFragmentToOffreMenuFragment(offreId)
  }
}
