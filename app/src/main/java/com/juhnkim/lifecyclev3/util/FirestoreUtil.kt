import android.annotation.SuppressLint
import com.google.firebase.firestore.FirebaseFirestore

object FirestoreUtil {
    @SuppressLint("StaticFieldLeak")
    private var instance: FirebaseFirestore? = null

    fun getInstance(): FirebaseFirestore {
        if (instance == null) {
            instance = FirebaseFirestore.getInstance()
        }
        return instance!!
    }
}