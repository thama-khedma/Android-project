package fragments

import android.os.Bundle
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.example.khedma.R
import kotlin.Int
import kotlin.String


public class MyOffreFragmentDirections private constructor() {
  private data class ActionMyOffresFragmentToOffreDetailsFragment(
    public val offreId: String
  ) : NavDirections {
    public override val actionId: Int =
        R.id.action_myOffresFragment_to_offreDetailsFragment

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("offreId", this.offreId)
        return result
      }
  }

  public companion object {
    public fun actionMyOffresFragmentToAddOffreFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_myOffresFragment_to_addOffreFragment)

    public fun actionMyOffresFragmentToOffreDetailsFragment(offreId: String):
        NavDirections = ActionMyOffresFragmentToOffreDetailsFragment(offreId)
  }


}
