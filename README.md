CanaryMod
=========

[![Build Status](https://ci.visualillusionsent.net/buildStatus/icon?job=CanaryMod)](https://ci.visualillusionsent.net/job/CanaryMod/)
[Latest Build](http://ci.visualillusionsent.net/job/CanaryMod/lastBuild/net.canarymod$CanaryMod/)  
[Latest Successful Build](http://ci.visualillusionsent.net/job/CanaryMod/lastSuccessfulBuild/net.canarymod$CanaryMod/)  

CanaryMod is a Minecraft Server wrapper and library with built-in data  
management features as well as player permissions and groups management.  
CanaryMod provides a stable and feature-rich framework that makes sure that  
Plugins written today, will still work in months without the need of updating  
on each Minecraft update. As server owner, this allows you to spend more time  
on managing your server, making it awesome, instead of waiting for all your  
plugins to update each time.  

As a Plugin Developer, our Mod allows you to concentrate on the things that make  
your Plugin great and unique - you won’t ever need to bother about implementing  
data storage or resolving dependencies with other plugins. We do the dirty work  
for you, you go and make amazing plugins. It’s as easy as that!  

This repository contains the wrapper/mod implementation.

Pull Requests
=============

It helps us when others take the time to submit fixes rather than just pointing out bugs/inconsistancies.  
However, We have standards for the sources we have. Things like formatting  
and generally following the [Sun/Oracle coding standards](http://www.oracle.com/technetwork/java/javase/documentation/codeconvtoc-136057.html)  
We also ask that you read and sign our [Contribitors License Agreement Form](https://dl.dropboxusercontent.com/u/25586491/Canary/CanaryModTeam_CLA.pdf)  
(Please note that Mailing Address may be omitted and if submitted will only be used as an alternative contact method if anything should change in the project)  

Source Formatting and requirements
-------------

* No tabs; use 4 spaces instead.
* No trailing whitespaces.
* No CRLF line endings, LF only.
  * Git can handle this by auto-converting CRLF line endings into LF when you commit, and vice versa when it checks out code onto your filesystem.  
    You can turn on this functionality with the core.autocrlf setting.  
    If you’re on a Windows machine, set it to true — this converts LF endings into CRLF when you check out code. (git config --global core.autocrlf true)  
  * Eclipse: http://stackoverflow.com/a/11596227/532590
  * NetBeans: http://stackoverflow.com/a/1866385/532590
* JavaDocs well written (as necessary)
* Matching how we format statements

License
-------

Copyright (c) 2012 - 2018, CanaryMod Team
Under the management of PlayBlack and Visual Illusions Entertainment  
All rights reserved.  
  
Redistribution and use in source and binary forms, with or without  
modification, are permitted provided that the following conditions are met:  
    * Redistributions of source code must retain the above copyright  
      notice, this list of conditions and the following disclaimer.  
    * Redistributions in binary form must reproduce the above copyright  
      notice, this list of conditions and the following disclaimer in the  
      documentation and/or other materials provided with the distribution.  
    * Neither the name of the FallenMoonNetwork/CanaryMod Team nor the  
      names of its contributors may be used to endorse or promote products  
      derived from this software without specific prior written permission.  
  
THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND  
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED  
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE  
DISCLAIMED. IN NO EVENT SHALL CANARYMOD TEAM OR ITS CONTRIBUTORS BE LIABLE FOR ANY  
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES  
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;  
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND  
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT  
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS  
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.  
  
Any source code from the Minecraft Server is not owned by CanaryMod Team, PlayBlack,  
Visual Illusions Entertainment, or its contributors and is not covered by above license.  
Usage of source code from the Minecraft Server is subject to the Minecraft End User License Agreement as set forth by Mojang AB.  
The Minecraft EULA can be viewed at https://account.mojang.com/documents/minecraft_eula  
CanaryMod Team, PlayBlack, Visual Illusions Entertainment, CanaryLib, CanaryMod, and its contributors  
are NOT affiliated with, endorsed, or sponsored by Mojang AB, makers of Minecraft.  
"Minecraft" is a trademark of Notch Development AB  
"CanaryMod" name is used with permission from FallenMoonNetwork.  
