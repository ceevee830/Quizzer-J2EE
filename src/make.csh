set nullwords = 1

local builddir, filelist, cp, args
set filelist=""
set cp=""
set args = $argv[0]
set builddir=..\build

if ($args == '') then
	# Build list of .java files in the cwd
	foreach file (`ls -1 *.java`)
		if ($filelist == "") then
			set filelist = $file
		else
			set filelist = $filelist $file
		end
	end

else
	set filelist = $args
endif

set cp = ..\lib\jbcl3.1.jar


# Compile list of .java files using list of .jar and .zip files
if (!-d $builddir) then
	md $builddir
end
javac -deprecation -sourcepath . -d $builddir -classpath $builddir^;$cp $filelist
cp *.jpg *.gif $builddir

