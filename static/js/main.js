// 添加简体时间
document.addEventListener('DOMContentLoaded', () => {
    
    // 1. 自动更新 Footer 年份
    const yearSpan = document.getElementById('year');
    if(yearSpan) {
        yearSpan.textContent = new Date().getFullYear();
    }

    // 2. 模拟博客文章数据
    const posts = [
        {
            title: "Analysis of Smart Grid Stability",
            date: "2025-05-12",
            category: "Research",
            summary: "Exploring the impact of renewable energy integration on grid frequency stability..."
        },
        {
            title: "My Workflow with Python & MATLAB",
            date: "2025-04-28",
            category: "Coding",
            summary: "How I set up my development environment for power system simulations."
        },
        {
            title: "Reflections on Year 2024",
            date: "2025-01-01",
            category: "Life",
            summary: "A summary of what I learned and achieved in the past year as a researcher."
        }
    ];

    // 3. 渲染文章列表
    const postListContainer = document.getElementById('post-list');

    if (postListContainer) {
        // 清空 Loading 文字
        postListContainer.innerHTML = '';

        posts.forEach(post => {
            // 创建 article 元素
            const article = document.createElement('article');
            article.className = 'post-card';

            // 填充内容
            article.innerHTML = `
                <h3 class="post-title">
                    <a href="#">${post.title}</a>
                </h3>
                <div class="post-meta">
                    <span>📅 ${post.date}</span> | 
                    <span>🏷️ ${post.category}</span>
                </div>
                <p class="post-summary">${post.summary}</p>
            `;

            // 添加到容器
            postListContainer.appendChild(article);
        });
    }


    // 弹窗--跳转到 番茄时钟
    const modal = document.getElementById('welcome-modal');
            const confirmBtn = document.getElementById('confirm-btn');
            const cancelBtn = document.getElementById('cancel-btn');
            
            // 假设您的番茄时钟页面文件名为 pomodoro-timer.html
            const targetUrl = 'pomodoro-timer.html'; 

            // 确认按钮：跳转到番茄时钟页面
            confirmBtn.addEventListener('click', () => {
                window.location.href = targetUrl;
            });

            // 取消按钮：关闭弹窗
            cancelBtn.addEventListener('click', () => {
                modal.style.display = 'none';
                // 如果需要，可以在这里让页面主体内容可见
            });

            // 可选：点击背景也关闭弹窗
            modal.addEventListener('click', (e) => {
                if (e.target === modal) {
                    modal.style.display = 'none';
                }
            });

            // 默认情况下，在页面加载完成时显示弹窗 (CSS 默认设置为可见)
});

