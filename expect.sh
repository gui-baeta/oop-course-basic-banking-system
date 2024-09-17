#!/usr/bin/expect

set passWord [lindex $argv 0];
set dirCommand [lindex $argv 1];
set cvsCommand [lindex $argv 2];
set cvsCommand_option [lindex $argv 3];

cd project;
if { $cvsCommand_option == ""} {
    spawn cvs "$cvsCommand";
} else {
    spawn cvs "$cvsCommand" "$cvsCommand_option";
}
expect "Password: ";
send "$passWord\r";
expect -re "\[#\$%]";
