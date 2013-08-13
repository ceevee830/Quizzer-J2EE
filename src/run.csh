set nullwords = 1

local cp, classToRun
set cp=""
set classToRun = "App"

foreach file (`ls -1 ..\...\*.jar ..\...\*.zip`)
	if ($cp == "") then
		set cp = $file
	else
		set cp = $cp^;$file
	end
end

java -classpath ..\build^;$cp $classToRun ..
# java -classpath $cp $classToRun


