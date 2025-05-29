import com.alexstyl.debugln.allowLogging
import com.alexstyl.debugln.debugln
import com.alexstyl.debugln.errorln
import com.alexstyl.debugln.infoln
import com.alexstyl.debugln.verboseln
import com.alexstyl.debugln.warnln

fun main() {
    // these will be printed in the console
    verboseln { "Verbose" }
    debugln { "Debug" }
    infoln { "Info" }
    warnln { "Warn" }
    errorln { "Error" }

    allowLogging = false
    // these won't be printed in the console
    verboseln { "Verbose" }
    debugln { "Debug" }
    infoln { "Info" }
    warnln { "Warn" }
    errorln { "Error" }
}