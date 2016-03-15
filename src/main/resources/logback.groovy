// Documentation on PatternLayout and TimeBasedRollingPolicy
// http://logback.qos.ch/manual/layouts.html
// http://logback.qos.ch/manual/appenders.html

import ch.qos.logback.classic.PatternLayout
import ch.qos.logback.classic.db.DBAppender
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.db.JNDIConnectionSource
import ch.qos.logback.core.encoder.LayoutWrappingEncoder
import ch.qos.logback.core.rolling.RollingFileAppender
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy

import static ch.qos.logback.classic.Level.OFF
import static ch.qos.logback.classic.Level.ERROR
import static ch.qos.logback.classic.Level.WARN
import static ch.qos.logback.classic.Level.INFO
import static ch.qos.logback.classic.Level.DEBUG
import static ch.qos.logback.classic.Level.TRACE
import static ch.qos.logback.classic.Level.ALL

def logbackdir = "/usr/local/logs/gtfs"

appender("STDOUT", ConsoleAppender) {
	encoder(LayoutWrappingEncoder) {
		layout(PatternLayout) {
			pattern = "%date{HH:mm:ss.SSS} [%mdc{remoteAddress}]-[%thread] %-5level | %logger{35}.%method[%line] | %message%n"
		}
	}
}

appender("ROLLING", RollingFileAppender) {
	rollingPolicy(TimeBasedRollingPolicy) {
		FileNamePattern = "${logbackdir}/gtfs.log.%d{yyyy-MM-dd}"
	}
	encoder(LayoutWrappingEncoder) {
		layout(PatternLayout) {
			pattern = "%date{HH:mm:ss.SSS} [%mdc{remoteAddress}]-[%thread] %-5level %logger{35}.%method[%line] | %message%n"
		}
	}
}

logger("com", INFO)
logger("org", INFO)
logger("net", INFO)

logger("com.3dx2", INFO)

logger("org.springframework.integration", WARN)
logger("org.springframework", WARN)
logger("org.hibernate", WARN)

root(INFO, ["STDOUT", "ROLLING"])
