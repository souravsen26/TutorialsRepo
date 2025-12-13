# 📘 TutorialsRepo

A personal repository to organize and track hands-on learning from various programming courses. Each course is structured in its own folder and may include personal projects, documentation, and external codebases integrated as Git submodules.

## 📁 Structure Overview
** To see the repo structure checkout the repo using "🔗 GIT clone and 🗒 Development Notes" mentioned below and open the readme.md file using Notepad++:

TutorialsRepo/
├── course1/
│   ├── tutorialCode-springframeworkgure/   # Submodule: Instructor's code
│   ├── ProjectDoc/                         # Notes, diagrams, and documentation
│   ├── myProject/                          # Your own implementation
│   └── README.MD/                          # Your own implementation
│
│
├── course2/
│   ├── tutorialCode-springframeworkgure/   # Submodule: Instructor's code
│   ├── ProjectDoc/                         # Notes, diagrams, and documentation
│   ├── myProject/                          # Your own implementation
│   └── README.MD/                          # Your own implementation
│
├── course3/
│   └── ...                                 # Future course content
│
│
└── README.md

---


## 🔗 GIT clone and 🗒 Development Notes

This repository uses Git submodules to include external codebases used in tutorials.
To clone this repository with all submodules:

bash
git clone --recurse-submodules https://github.com/souravsen26/TutorialsRepo.git

If you've already cloned it without submodules:
bash
git submodule update --init --recursive


---

## 🧭 OTHER

> 📌 **Local Checkout Path:**  
> This repository is currently cloned to:  
> `C:\Users\soura\My Projects\Misc. Projects\TutorialRepo`  
> This note is for personal reference to avoid losing track of the local workspace.