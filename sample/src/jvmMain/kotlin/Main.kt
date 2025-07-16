import com.alexstyl.debugln.LoggingLevel
import com.alexstyl.debugln.allowLogging
import com.alexstyl.debugln.debugln
import com.alexstyl.debugln.errorln
import com.alexstyl.debugln.infoln
import com.alexstyl.debugln.loggingLevel
import com.alexstyl.debugln.verboseln
import com.alexstyl.debugln.warnln

fun main() {
    // these will be printed in the console
    verboseln { "Verbose" }
    debugln { "Debug" }
    infoln { "Info" }
    warnln { "Warn" }
    errorln { "Error" }

    // this will print only debug and above
    loggingLevel = LoggingLevel.DEBUG
    verboseln { "Verbose" }
    debugln { "Debug" }
    infoln { "Info" }
    warnln { "Warn" }
    errorln { "Error" }

    // this turns off all logging
    allowLogging = false
    verboseln { "Verbose" }
    debugln { "Debug" }
    infoln { "Info" }
    warnln { "Warn" }
    errorln { "Error" }
}