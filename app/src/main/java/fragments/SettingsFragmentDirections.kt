package fragments

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.example.khedma.R

public class SettingsFragmentDirections private constructor() {
  public companion object {
    public fun actionSettingsFragmentToProfileFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_settingsFragment_to_profileFragment)

    public fun actionSettingsFragmentToChangePasswordFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_settingsFragment_to_changePasswordFragment)
  }
}
