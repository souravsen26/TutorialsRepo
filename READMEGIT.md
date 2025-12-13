# 📘 TutorialsRepo

How was this project created ?
> If you look at the structure of the project it has two code bases per course level. 1 -> original which is added as submodule from instructors code base. 2 my own implementation.

---

Following steps were taken.
> Created a new Repo TutorialRepo.
> Taken Git clone(checkout) of the same in my local.
> Then created a dummy README.md file and then executed  -----> git add . ----> git commit -m "First commit"  ----> git push -u origin main
> Then added the instructors repo as sub module using 
>		git submodule add https://github.com/springframeworkguru/spring5webapp.git tutorialCode-springframeworkgure
> 		

---

## 📁 Structure Overview

```
TutorialsRepo/
├── course1/
│   ├── tutorialCode-springframeworkgure/   # Submodule: Instructor's code
│   ├── ProjectDoc/                         # Notes, diagrams, and documentation
│   ├── myProject/                          # Your own implementation
│   └── README.MD/                          # Individual Project Level Info
│
│
├── course2/
│   ├── tutorialCode-springframeworkgure/   # Submodule: Instructor's code
│   ├── ProjectDoc/                         # Notes, diagrams, and documentation
│   ├── myProject/                          # Your own implementation
│   └── README.MD/                          # Individual Project Level Info
│
├── course3/
│   └── ...                                 # Future course content
│
│
└── README.md
```
---


## 

Adding a Git submodule embeds another Git repository (like a library or component) 
as a subdirectory in your main project, essentially linking to a specific commit of that external repo, 
creating a .gitmodules file to track it, and adding the submodule's folder (which initially appears empty) to your main project. 
It lets you manage dependencies cleanly, but requires extra commands (like git submodule update) to fetch submodule content after cloning, 
as they don't automatically populate. 

What happens immediately (after git submodule add <URL>)
A new folder appears: A directory named after the submodule (e.g., library-name) is created in your project.
It's initially empty: This folder will look empty because Git only records which commit (SHA-1) of the external repo to use, not the files themselves.
.gitmodules file is created/updated: A configuration file (.gitmodules) is added or modified, mapping the submodule's URL to its local path.
Changes are staged: The new folder and .gitmodules file are marked as changes in your main repository, ready for commit. 
What happens after committing and pushing
Your main repository now contains a pointer (the specific commit ID) to the external repository, not its full history. 
What happens when someone clones your repo
They get your main project, but the submodule directory will still be empty.
They must run git submodule update --init` (or --recursive) to download the submodule's content and check out the correct commit. 
Key Concept: Pinning to a Commit
Submodules are "pinned" to a specific commit, not a branch. This means even if the external repo's main branch updates, your submodule won't automatically update until you manually update the reference in your main project. 
When to use them
To include third-party libraries or shared components that are versioned independently. 