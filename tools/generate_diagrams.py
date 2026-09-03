import sys, os, json, glob
sys.path.insert(0, os.path.dirname(__file__))
from icon_lib import render_lesson_card

COLORS = {
    "ai": (106, 90, 205),
    "cloud_computing": (30, 144, 255),
    "cybersecurity": (211, 47, 47),
    "data_science": (0, 150, 136),
    "mobile_development": (233, 30, 99),
    "networking": (56, 142, 60),
    "programming": (255, 143, 0),
    "web_development": (3, 155, 229),
}

# lesson_id: (icon, title, [chips])
MANIFEST = {
    # ---------- AI ----------
    "ai-b1": ("brain", "Menene AI?", ["Artificial Intelligence"]),
    "ai-b2": ("chart", "Yadda AI Ke Koyo", ["Data", "Training", "Prediction"]),
    "ai-b3": ("globe", "AI a Rayuwarmu", ["Maps", "Netflix", "Face Unlock"]),
    "ai-b4": ("chat", "Chatbots", ["ChatGPT", "Claude", "Gemini"]),
    "ai-b5": ("chat", "Prompting", ["Gajeren Tambaya", "Cikakkiyar Tambaya"]),
    "ai-b6": ("warning", "Iyakokin AI", ["Hallucination", "Bias"]),
    "ai-i1": ("compare", "Narrow AI vs AGI", ["Aiki Daya", "Kowane Fanni"]),
    "ai-i2": ("gear", "Machine Learning", ["Supervised", "Unsupervised"]),
    "ai-i3": ("puzzle", "Kayan Aikin AI", ["ChatGPT", "Midjourney", "Grammarly"]),
    "ai-i4": ("briefcase", "AI Wajen Aiki", ["Rubutu", "Takaitawa", "Ra'ayoyi"]),
    "ai-i5": ("compare", "AI Ethics", ["Bias", "Privacy", "Deepfakes"]),
    "ai-i6": ("briefcase", "AI da Ayyukan Yi", ["Sabbin Ayyuka", "Prompt Engineer"]),
    "ai-a2": ("chat", "Large Language Models", ["Transformer", "Hasashen Kalma"]),
    "ai-a3": ("code", "Generative AI", ["Rubutu", "Hoto", "Bidiyo"]),
    "ai-a4": ("gear", "Fine-tuning vs RAG", ["Canza Model", "Kara Bayanai"]),
    "ai-a5": ("gear", "AI Agents", ["Manufa", "Mataki", "Sakamako"]),
    "ai-a6": ("briefcase", "Sana'o'in AI", ["Prompt Engineer", "ML Engineer"]),

    # ---------- Cloud Computing ----------
    "cloud-b1": ("cloud", "Cloud Computing", ["Kwamfuta ta Nesa"]),
    "cloud-b2": ("container", "IaaS / PaaS / SaaS", ["IaaS", "PaaS", "SaaS"]),
    "cloud-b3": ("globe", "Manyan Kamfanoni", ["AWS", "Azure", "Google Cloud"]),
    "cloud-b4": ("briefcase", "Amfanin Cloud", ["Kasuwanci", "Daidaikun Mutane"]),
    "cloud-b5": ("database", "Cloud Storage", ["Google Drive", "Dropbox"]),
    "cloud-b6": ("checklist", "Fara Amfani da Cloud", ["Bude Asusu", "Loda Fayil"]),
    "cloud-i1": ("container", "Virtualization/Containers", ["VM", "Docker"]),
    "cloud-i2": ("speed", "Scalability/Elasticity", ["Girma", "Rage"]),
    "cloud-i3": ("rocket", "Cloud Migration", ["Lift & Shift", "Refactoring"]),
    "cloud-i4": ("money", "Sarrafa Kudi", ["Cloud Waste", "Cost Monitoring"]),
    "cloud-i5": ("puzzle", "APIs a Cloud", ["Ajiya", "AI", "Sanarwa"]),
    "cloud-i6": ("network", "Multi-cloud/Hybrid", ["AWS+GCP", "Public+Private"]),
    "cloud-a1": ("container", "Kubernetes", ["Containers", "Orchestration"]),
    "cloud-a2": ("rocket", "Serverless Computing", ["AWS Lambda"]),
    "cloud-a3": ("gear", "Architecture Patterns", ["Microservices", "Load Balancing"]),
    "cloud-a4": ("code", "DevOps / CI-CD", ["Gina", "Gwaji", "Saki"]),
    "cloud-a5": ("database", "Cloud Databases", ["SQL", "NoSQL"]),
    "cloud-a6": ("briefcase", "Sana'o'in Cloud", ["Cloud Engineer", "Cloud Architect"]),

    # ---------- Cybersecurity (cyber-b1, cyber-b3 already have custom images; cyber-a1 interactive) ----------
    "cyber-b2": ("key", "Kalmomin Sirri", ["10+ Haruffa", "Alamomi"]),
    "cyber-b4": ("bug", "Malware", ["Virus", "Trojan", "Ransomware"]),
    "cyber-b5": ("mask", "Social Engineering", ["Tsoro", "Gaggawa"]),
    "cyber-b6": ("wifi", "Wi-Fi Security", ["VPN", "Kalmar Sirri"]),
    "cyber-i1": ("phone", "Tsaro na Waya", ["PIN", "Find My Device"]),
    "cyber-i2": ("chat", "Sada Zumunta", ["Privacy Settings"]),
    "cyber-i3": ("warning", "Zamba ta Kan Layi", ["SIM Swap", "Lottery Scam"]),
    "cyber-i4": ("money", "Tsaro na Kudi", ["HTTPS", "CVV", "OTP"]),
    "cyber-i5": ("key", "Two-Factor Authentication", ["Password", "SMS Code"]),
    "cyber-i6": ("checklist", "Security Habits", ["Update", "Backup", "2FA"]),
    "cyber-a2": ("lock", "Encryption", ["Symmetric", "Asymmetric"]),
    "cyber-a3": ("cloud", "Cloud Security", ["Shared Responsibility"]),
    "cyber-a4": ("magnifier", "Ethical Hacking", ["Penetration Test", "White-Hat"]),
    "cyber-a5": ("medical", "Incident Response", ["Detect", "Contain", "Recover"]),
    "cyber-a6": ("shop", "Kariya ga Karamin Kasuwanci", ["Horaswa", "Backup"]),

    # ---------- Data Science ----------
    "ds-b1": ("chart", "Data Science", ["Data + Stats + Code"]),
    "ds-b2": ("database", "Nau'ukan Bayanai", ["Structured", "Unstructured"]),
    "ds-b3": ("chart", "Excel/Spreadsheets", ["Pivot Table"]),
    "ds-b4": ("chart", "Data Visualization", ["Bar", "Line", "Pie"]),
    "ds-b5": ("chart", "Statistics", ["Mean", "Median"]),
    "ds-b6": ("code", "Python & Pandas", ["Pandas", "Matplotlib"]),
    "ds-i1": ("checklist", "Data Cleaning", ["Missing Values", "Duplicates"]),
    "ds-i2": ("database", "SQL", ["SELECT", "WHERE"]),
    "ds-i3": ("gear", "ML for Data Science", ["Training Data", "Test Data"]),
    "ds-i4": ("compare", "A/B Testing", ["Zabi A", "Zabi B"]),
    "ds-i5": ("chart", "Dashboards", ["Tableau", "Power BI"]),
    "ds-i6": ("compare", "Data Ethics", ["Izini", "Bias"]),
    "ds-a1": ("database", "Big Data", ["Volume", "Velocity", "Variety"]),
    "ds-a2": ("chart", "Predictive Modeling", ["Features", "Hasashe"]),
    "ds-a3": ("brain", "Deep Learning", ["Neural Networks"]),
    "ds-a4": ("gear", "Data Engineering", ["Data Pipeline"]),
    "ds-a5": ("briefcase", "Business Analytics", ["Yanke Shawara"]),
    "ds-a6": ("briefcase", "Sana'o'in Data Science", ["Data Analyst", "Data Engineer"]),

    # ---------- Mobile Development ----------
    "mob-b1": ("phone", "Mobile App Development", ["Android", "iOS"]),
    "mob-b2": ("compare", "Android vs iOS", ["Kotlin/Java", "Swift"]),
    "mob-b3": ("compare", "Native vs Cross-Platform", ["Performance", "Code Daya"]),
    "mob-b4": ("phone", "UI/UX", ["Kamanni", "Gogewa"]),
    "mob-b5": ("gear", "Android Studio/Xcode", ["Emulator", "Debugging"]),
    "mob-b6": ("code", "App na Farko", ["Hello World"]),
    "mob-i1": ("code", "Flutter/React Native", ["Dart", "JavaScript"]),
    "mob-i2": ("database", "Local Storage", ["SharedPreferences", "SQLite"]),
    "mob-i3": ("puzzle", "APIs/Backend", ["REST API", "JSON"]),
    "mob-i4": ("magnifier", "Testing/Debugging", ["Emulator", "Real Device"]),
    "mob-i5": ("shop", "Buga App", ["Play Store", "App Store"]),
    "mob-i6": ("money", "Monetization", ["Ads", "In-App Purchase"]),
    "mob-a1": ("chat", "Push Notifications", ["FCM", "APNs"]),
    "mob-a2": ("lock", "Mobile Security", ["HTTPS", "API Keys"]),
    "mob-a3": ("speed", "Performance", ["Baturi", "Sauri"]),
    "mob-a4": ("code", "CI/CD Mobile", ["Fastlane"]),
    "mob-a5": ("network", "Wearables/IoT", ["Apple Watch", "BLE"]),
    "mob-a6": ("briefcase", "Sana'o'in Mobile", ["Android Dev", "iOS Dev"]),

    # ---------- Networking (net-a1 interactive) ----------
    "net-b1": ("network", "Cibiyar Sadarwa", ["Na'urori + Sadarwa"]),
    "net-b2": ("globe", "IP Address", ["192.168.1.1"]),
    "net-b3": ("gear", "Router/Switch/Modem", ["Modem", "Router", "Switch"]),
    "net-b4": ("wifi", "Wi-Fi vs Ethernet", ["Wireless", "Kebul"]),
    "net-b5": ("globe", "Yadda Intanet Ke Aiki", ["Kebul na Karkashin Teku"]),
    "net-b6": ("warning", "Matsalolin Network", ["Restart Router"]),
    "net-i1": ("network", "OSI Model", ["7 Layers"]),
    "net-i2": ("network", "TCP/IP", ["TCP", "IP", "UDP"]),
    "net-i3": ("globe", "DNS", ["google.com", "IP Address"]),
    "net-i4": ("speed", "Bandwidth/Latency", ["Girma", "Sauri"]),
    "net-i5": ("lock", "VPN", ["Encrypted Tunnel"]),
    "net-i6": ("shield", "Network Security", ["WPA3", "SSID"]),
    "net-a2": ("compare", "Load Balancing", ["Server 1", "Server 2"]),
    "net-a3": ("rocket", "HTTP/3 & QUIC", ["Google's QUIC"]),
    "net-a4": ("gear", "SDN", ["Control Plane", "Data Plane"]),
    "net-a5": ("wifi", "5G", ["Network Slicing"]),
    "net-a6": ("briefcase", "Sana'o'in Networking", ["Network Admin"]),

    # ---------- Programming ----------
    "prog-b1": ("code", "Programming", ["Umarni ga Kwamfuta"]),
    "prog-b2": ("database", "Variables & Data Types", ["String", "Integer", "Boolean"]),
    "prog-b3": ("compare", "If / Else", ["Sharadi"]),
    "prog-b4": ("gear", "Loops", ["For", "While"]),
    "prog-b5": ("puzzle", "Functions", ["Sake Amfani"]),
    "prog-b6": ("book", "Zaben Yare", ["Python", "JavaScript"]),
    "prog-i1": ("database", "Arrays/Lists", ["Index 0,1,2..."]),
    "prog-i2": ("gear", "OOP", ["Class", "Object"]),
    "prog-i3": ("network", "Git & Version Control", ["Commit", "GitHub"]),
    "prog-i4": ("magnifier", "Debugging", ["Bug", "Print Statement"]),
    "prog-i5": ("puzzle", "APIs", ["Request", "JSON Response"]),
    "prog-i6": ("book", "Algorithms", ["Matakan Warware Matsala"]),
    "prog-a1": ("network", "Trees & Graphs", ["Root", "Nodes"]),
    "prog-a2": ("gear", "Design Patterns", ["Singleton", "Observer"]),
    "prog-a3": ("checklist", "Testing", ["Unit Test", "Automation"]),
    "prog-a4": ("gear", "Concurrency", ["Ayyuka Da Yawa"]),
    "prog-a5": ("shield", "Secure Coding", ["SQL Injection"]),
    "prog-a6": ("briefcase", "Sana'o'in Software", ["Frontend", "Backend", "Full-Stack"]),

    # ---------- Web Development ----------
    "web-b1": ("globe", "Yadda Web Ke Aiki", ["Browser + Server"]),
    "web-b2": ("code", "HTML", ["Tags", "Structure"]),
    "web-b3": ("code", "CSS", ["Launi", "Girma", "Tsari"]),
    "web-b4": ("code", "JavaScript", ["Mu'amala"]),
    "web-b5": ("gear", "Browser", ["Rendering Engine"]),
    "web-b6": ("book", "Shafi na Farko", ["index.html"]),
    "web-i1": ("phone", "Responsive Design", ["Media Queries"]),
    "web-i2": ("puzzle", "Frontend Frameworks", ["React", "Vue"]),
    "web-i3": ("database", "Backend", ["Server", "Database"]),
    "web-i4": ("database", "Databases", ["MySQL", "MongoDB"]),
    "web-i5": ("puzzle", "REST APIs", ["Frontend", "Backend", "JSON"]),
    "web-i6": ("rocket", "Hosting/Deployment", ["Netlify", "Vercel"]),
    "web-a1": ("network", "Full-Stack", ["React", "Node.js", "PostgreSQL"]),
    "web-a2": ("speed", "Performance", ["Caching", "Lazy Loading"]),
    "web-a3": ("shield", "Web Security", ["XSS", "CSRF"]),
    "web-a4": ("phone", "PWA", ["Offline", "Push Notifications"]),
    "web-a5": ("compare", "SSR vs CSR", ["Server", "Client"]),
    "web-a6": ("briefcase", "Sana'o'in Web Dev", ["Frontend Dev", "UI/UX Designer"]),
}

def main():
    courses_dir = os.path.join(os.path.dirname(__file__), "..", "app", "src", "main", "assets", "courses")
    images_dir = os.path.join(os.path.dirname(__file__), "..", "app", "src", "main", "assets", "images")

    generated = 0
    updated_lessons = 0
    skipped = []

    for fpath in sorted(glob.glob(f"{courses_dir}/*.json")):
        course_key = fpath.split("/")[-1].replace(".json", "")
        color = COLORS[course_key]
        d = json.load(open(fpath, encoding="utf-8"))
        changed = False
        for lvl in d["levels"]:
            for lesson in lvl["lessons"]:
                lid = lesson["id"]
                if lesson.get("diagram") is not None:
                    continue  # already has interactive or existing custom image
                if lid not in MANIFEST:
                    skipped.append(lid)
                    continue
                icon, title, chips = MANIFEST[lid]
                asset_name = f"{lid}.png"
                render_lesson_card(f"{images_dir}/{asset_name}", icon, title, chips, color)
                generated += 1
                lesson["diagram"] = {
                    "type": "image",
                    "asset": asset_name,
                    "caption": lesson["summary"]
                }
                changed = True
                updated_lessons += 1
        if changed:
            with open(fpath, "w", encoding="utf-8") as out:
                json.dump(d, out, ensure_ascii=False, indent=2)

    print(f"Generated {generated} images, updated {updated_lessons} lessons.")
    if skipped:
        print("Lessons in files but missing from MANIFEST:", skipped)

if __name__ == "__main__":
    main()
