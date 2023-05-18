package fragments

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.String
import kotlin.jvm.JvmStatic

public data class EditOffreFragmentArgs(
  public val offreId: String
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putString("offreId", this.offreId)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("offreId", this.offreId)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): EditOffreFragmentArgs {
      bundle.setClassLoader(EditOffreFragmentArgs::class.java.classLoader)
      val __offreId : String?
      if (bundle.containsKey("offreId")) {
        __offreId = bundle.getString("offreId")
        if (__offreId == null) {
          throw IllegalArgumentException("Argument \"offreId\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"offreId\" is missing and does not have an android:defaultValue")
      }
      return EditOffreFragmentArgs(__offreId)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle):
            EditOffreFragmentArgs {
      val __offreId : String?
      if (savedStateHandle.contains("offreId")) {
        __offreId = savedStateHandle["offreId"]
        if (__offreId == null) {
          throw IllegalArgumentException("Argument \"offreId\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"offreId\" is missing and does not have an android:defaultValue")
      }
      return EditOffreFragmentArgs(__offreId)
    }
  }
}
