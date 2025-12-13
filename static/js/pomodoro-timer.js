// pomodoro-timer.js (最终完善版本)

// --- 核心状态和配置 ---
let isRunning = false;
let intervalId = null;
let currentSession = "work"; // work, shortBreak, longBreak
let pomodorosCompleted = 0; // 记录完成的工作周期数
const INITIAL_RADIUS = 144;
const CIRCUMFERENCE = 2 * Math.PI * INITIAL_RADIUS;

let settings = {
    workDuration: 25,
    shortBreak: 5,
    longBreak: 15,
    pomodorosToLongBreak: 4, // 4个工作周期后进行长休息
    volume: 50,
    theme: 'light', 
};

let timeLeft; 
let tasks = [];
let history = []; 

// --- DOM 引用 ---
const timeDisplay = document.getElementById("time-left");
const startPauseBtn = document.getElementById("start-pause-btn");
const resetBtn = document.getElementById("reset-btn");
const progressIndicator = document.getElementById("progress-indicator");
const sessionTabs = document.getElementById("session-tabs"); // 引用会话标签容器
const taskList = document.getElementById("tasks");
const taskInput = document.getElementById("task-input");
const addTaskBtn = document.getElementById("add-task-btn");
const settingsPanel = document.getElementById("settings-panel");
const settingsToggle = document.getElementById("settings-toggle");
const saveSettingsBtn = document.getElementById("save-settings-btn");
const modeToggleBtn = document.getElementById("theme-toggle");

// --- 初始化与持久化 ---

function loadSettings() {
    const storedSettings = JSON.parse(localStorage.getItem("pomodoroSettings"));
    if (storedSettings) {
        settings = { ...settings, ...storedSettings };
    }
    document.body.setAttribute('data-theme', settings.theme); 
    // 假设主题切换图标为 🎨 和 ☀️/🌙
    modeToggleBtn.textContent = (settings.theme === 'dark') ? "☀️" : "🎨"; 
}

function updateTimeLeft() {
    if (currentSession === "work") return settings.workDuration * 60;
    if (currentSession === "shortBreak") return settings.shortBreak * 60;
    if (currentSession === "longBreak") return settings.longBreak * 60;
    return 0;
}

function initializeApp() {
    loadSettings();
    loadTasks();
    
    // ⭐ 修复关键错误：初始化时间
    timeLeft = updateTimeLeft();
    renderTimer(timeLeft, timeLeft);
    updateSessionTitle(currentSession);
}

// --- 计时器核心功能 ---

function renderTimer(currentSeconds, totalSeconds) {
    const minutes = Math.floor(currentSeconds / 60);
    const seconds = currentSeconds % 60;
    timeDisplay.textContent = `${minutes}:${seconds < 10 ? "0" : ""}${seconds}`;

    // 更新进度环
    const dashoffset = CIRCUMFERENCE * (currentSeconds / totalSeconds);
    progressIndicator.style.strokeDasharray = CIRCUMFERENCE;
    progressIndicator.style.strokeDashoffset = dashoffset;
}

function updateSessionTitle(session) {
    // 更改进度条颜色
    let color;
    if (session === 'work') {
        color = '#4CAF50'; // 绿色
    } else if (session === 'shortBreak') {
        color = '#2196F3'; // 蓝色
    } else {
        color = '#F44336'; // 红色 (长休息)
    }
    progressIndicator.style.stroke = color;
    
    // 切换标签的激活状态
    document.querySelectorAll('#session-tabs button').forEach(btn => {
        btn.classList.remove('active');
        if (btn.dataset.session === session) {
            btn.classList.add('active');
        }
    });
}

function startPauseTimer() {
    if (isRunning) {
        // 暂停：清除定时器
        clearInterval(intervalId);
        startPauseBtn.textContent = "继续 (Space)";
        isRunning = false;
    } else {
        // 开始/继续：设置定时器
        startPauseBtn.textContent = "暂停 (Space)";
        isRunning = true;
        
        let totalTime = updateTimeLeft();
        // 如果是新会话，重置 timeLeft 到总时间
        if (timeLeft <= 0 || timeLeft > totalTime) { 
            timeLeft = totalTime;
        }

        intervalId = setInterval(() => {
            timeLeft--;
            renderTimer(timeLeft, totalTime);

            if (timeLeft <= 0) {
                clearInterval(intervalId);
                // logSessionEnd(currentSession, totalTime); 
                // playSound(); 
                // sendNotification("时间到！", `开始${currentSession === 'work' ? '休息' : '工作'}。`); 
                changeSession(true); // 自动切换
            }
        }, 1000);
    }
}

function resetTimer() {
    clearInterval(intervalId);
    isRunning = false;
    timeLeft = updateTimeLeft(); // 重置为当前会话的默认时间
    startPauseBtn.textContent = "开始 (Space)";
    renderTimer(timeLeft, timeLeft);
}

/**
 * 切换会话类型
 * @param {boolean} auto - 是否是自动切换 (计时器结束)，自动切换才会累加 pomodorosCompleted
 */
function changeSession(auto = false) {
    // 停止当前计时器
    clearInterval(intervalId);
    isRunning = false;
    startPauseBtn.textContent = "开始 (Space)";

    if (auto) {
        // 自动切换逻辑 (工作 -> 短休息/长休息 -> 工作)
        if (currentSession === "work") {
            pomodorosCompleted++;
            if (pomodorosCompleted % settings.pomodorosToLongBreak === 0) {
                currentSession = "longBreak";
            } else {
                currentSession = "shortBreak";
            }
        } else {
            // 休息结束，返回工作
            currentSession = "work";
        }
    } 
    // 如果是手动点击按钮，currentSession 会在事件监听器中更新

    // 更新时间显示和标题
    timeLeft = updateTimeLeft();
    updateSessionTitle(currentSession);
    renderTimer(timeLeft, timeLeft);
}

/**
 * 手动切换会话
 * @param {string} sessionType - work, shortBreak, or longBreak
 */
function switchSessionManual(sessionType) {
    if (currentSession === sessionType) return; // 如果已经是当前会话，不操作

    currentSession = sessionType;
    changeSession(false); // 手动切换，不触发自动累加和检查
}


// --- 任务管理（代码保持不变，已在上一轮修复） ---

function saveTasks() { /* ... */ }
function loadTasks() { 
    tasks = JSON.parse(localStorage.getItem("pomodoroTasks")) || [];
    renderTasks();
}
function renderTasks() { 
    taskList.innerHTML = "";
    tasks.forEach((task, index) => {
        const li = document.createElement("li");
        li.textContent = task.name;
        li.className = task.completed ? "completed" : "";
        li.onclick = () => toggleTaskCompletion(index);
        
        const deleteBtn = document.createElement("span");
        deleteBtn.textContent = " ❌";
        deleteBtn.style.cursor = "pointer";
        deleteBtn.onclick = (e) => {
            e.stopPropagation();
            deleteTask(index);
        };
        
        li.appendChild(deleteBtn);
        taskList.appendChild(li);
    });
    saveTasks();
}
function addTask() {
    const taskName = taskInput.value.trim();
    if (taskName !== "") {
        tasks.push({ name: taskName, completed: false });
        renderTasks();
        taskInput.value = "";
    }
}
function toggleTaskCompletion(index) {
    tasks[index].completed = !tasks[index].completed;
    renderTasks();
}
function deleteTask(index) {
    tasks.splice(index, 1);
    renderTasks();
}

// --- 模式切换（代码保持不变） ---
function toggleDarkMode() {
    const newTheme = document.body.getAttribute('data-theme') === 'light' ? 'dark' : 'light';
    document.body.setAttribute('data-theme', newTheme);
    settings.theme = newTheme;
    localStorage.setItem("pomodoroSettings", JSON.stringify(settings));
    modeToggleBtn.textContent = (newTheme === 'dark') ? "☀️" : "🎨";
    renderTimer(timeLeft, updateTimeLeft());
}


// --- 事件监听器 ---

startPauseBtn.addEventListener("click", startPauseTimer);
resetBtn.addEventListener("click", resetTimer);
document.getElementById("skip-btn").addEventListener("click", () => changeSession(false)); // 跳过按钮触发手动切换

// ⭐ 修复关键错误：手动切换会话的事件监听器
sessionTabs.addEventListener('click', (e) => {
    if (e.target.tagName === 'BUTTON' && e.target.dataset.session) {
        switchSessionManual(e.target.dataset.session);
    }
});

addTaskBtn.addEventListener("click", addTask);
taskInput.addEventListener("keypress", (e) => {
    if (e.key === 'Enter') {
        addTask();
    }
});
settingsToggle.addEventListener("click", () => {
    settingsPanel.classList.toggle("hidden");
});
// ⚠️ 注意：保存设置的逻辑 `saveSettings` 函数未提供，需您自己补充！
modeToggleBtn.addEventListener("click", toggleDarkMode);


// --- 启动应用 ---
initializeApp();