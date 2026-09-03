import sys, os, json, glob
sys.path.insert(0, os.path.dirname(__file__))
from icon_lib import (render_lesson_card, render_stack, render_flow, render_compare,
                       render_hub, render_client_server, render_icon_row, render_tree, render_bars)

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

# Each entry: lesson_id -> (template, title, *args-for-that-template)
# templates: card(chips) / stack(layers) / flow(steps) / compare(lt,lp,rt,rp) /
#            hub(center,satellites) / cs(left,right,req,resp) / iconrow(items) /
#            tree(root,children) / bars(items[(label,val)])
M = {}

# ================= AI =================
M["ai-b1"]  = ("card", "Menene AI?", ["Artificial Intelligence"])
M["ai-b2"]  = ("flow", "Yadda AI Ke Koyo", ["Bayanai Masu Yawa (Data)", "Horaswa (Training)", "Gane Sifofi (Patterns)", "Hasashe/Amsa"])
M["ai-b3"]  = ("iconrow", "AI a Rayuwarmu", [("globe","Google Maps"),("chat","Netflix/YouTube"),("phone","Face Unlock"),("money","Bank Fraud Detection")])
M["ai-b4"]  = ("iconrow", "AI Chatbots", [("chat","ChatGPT"),("chat","Claude"),("chat","Gemini")])
M["ai-b5"]  = ("compare", "Prompting", "Gajeren Tambaya", ["\"Ka koya mani AI\"", "Amsa maras cikakke"], "Cikakkiyar Tambaya", ["\"Ka koya AI a takaice ga sabon dan koyo\"", "Amsa mai dacewa"])
M["ai-b6"]  = ("iconrow", "Iyakokin AI", [("warning","Hallucination (Kuskure)"),("compare","Bias (Son Zuciya)")])
M["ai-i1"]  = ("compare", "Narrow AI vs AGI", "Narrow AI", ["Aiki ɗaya kacal", "Yana wanzuwa a yau"], "AGI (General AI)", ["Kowane fanni kamar mutum", "Har yanzu ra'ayi ne"])
M["ai-i2"]  = ("compare", "Machine Learning", "Supervised Learning", ["Bayanai + Amsoshi", "Misali: Hoto + Suna"], "Unsupervised Learning", ["Bayanai kadai", "AI ta gano sifofi da kanta"])
M["ai-i3"]  = ("iconrow", "Kayan Aikin AI", [("chat","ChatGPT/Claude"),("code","Midjourney/DALL-E"),("checklist","Grammarly"),("chart","Excel AI")])
M["ai-i4"]  = ("flow", "AI Wajen Aiki da Karatu", ["Ka Bayar da Tambaya", "AI Ta Bayar da Amsa", "Ka Duba Amsar", "Ka Yi Amfani da Ita"])
M["ai-i5"]  = ("iconrow", "AI Ethics", [("compare","Bias"),("lock","Privacy"),("mask","Deepfakes")])
M["ai-i6"]  = ("compare", "AI da Ayyukan Yi", "Ayyuka Masu Maimaici", ["Na iya raguwa"], "Sabbin Ayyuka", ["Prompt Engineer", "AI Trainer"])
M["ai-a2"]  = ("flow", "Yadda LLM Ke Gina Jimla", ["Karban Rubutunka", "Duba Mahallin (Context)", "Hasashen Kalma ta Gaba", "Maimaita Har Amsa Ta Kammala"])
M["ai-a3"]  = ("iconrow", "Generative AI", [("code","Rubutu (ChatGPT)"),("chart","Hoto (DALL-E)"),("phone","Bidiyo"),("chat","Sauti")])
M["ai-a4"]  = ("compare", "Fine-tuning vs RAG", "Fine-tuning", ["Kara horas da model", "Model din ya canza"], "RAG", ["Neman bayani a database", "Model din bai canza ba"])
M["ai-a5"]  = ("flow", "Yadda AI Agent Ke Aiki", ["An Bada Manufa", "Ya Tsara Matakai", "Ya Aiwatar", "Ya Duba Sakamako"])
M["ai-a6"]  = ("iconrow", "Sana'o'in AI", [("briefcase","Prompt Engineer"),("briefcase","AI Product Manager"),("briefcase","ML Engineer"),("briefcase","Data Scientist")])

# ================= Cloud Computing =================
M["cloud-b1"] = ("cs", "Cloud Computing", "Wayarka/Kwamfutarka", "Sabar Cloud (AWS, Google)", "Bukatar Aiki", "Sakamakon Aiki")
M["cloud-b2"] = ("stack", "IaaS / PaaS / SaaS", ["SaaS - Software Shirye don Amfani (Gmail)", "PaaS - Dandali don Gina App (App Engine)", "IaaS - Kwamfuta + Ajiya Kawai (EC2)"])
M["cloud-b3"] = ("iconrow", "Manyan Kamfanonin Cloud", [("cloud","AWS (Amazon)"),("cloud","Microsoft Azure"),("cloud","Google Cloud")])
M["cloud-b4"] = ("compare", "Amfanin Cloud", "Kasuwanci", ["Rage kuɗin farawa", "Girma a hankali"], "Daidaikun Mutane", ["Ajiye hotuna cikin aminci", "Shiga bayanai daga ko'ina"])
M["cloud-b5"] = ("iconrow", "Cloud Storage", [("database","Google Drive"),("database","Dropbox"),("database","OneDrive")])
M["cloud-b6"] = ("flow", "Fara Amfani da Cloud", ["Bude Asusu", "Loda Fayil (Upload)", "Raba Link", "Kunna 2FA"])
M["cloud-i1"] = ("compare", "Virtualization vs Containers", "Virtual Machine", ["Kwaikwayon dukkan kwamfuta", "Nauyi, jinkiri"], "Container (Docker)", ["App kadai + bukatunta", "Sauki, sauri"])
M["cloud-i2"] = ("bars", "Scalability/Elasticity", [("Yau da Kullum", 0.35), ("Lokacin Sallah (Girma)", 1.0)])
M["cloud-i3"] = ("flow", "Cloud Migration", ["Tantance App", "Lift & Shift ko Refactor", "Canja Bayanai", "Gwaji da Kaddamarwa"])
M["cloud-i4"] = ("compare", "Sarrafa Kuɗi a Cloud", "Ba a Kula ba", ["Sabar suna gudana banza", "Cloud Waste"], "Ana Kula da Kyau", ["Cost Monitoring Tools", "Kashe Sabar Marasa Amfani"])
M["cloud-i5"] = ("hub", "APIs a Cloud", "App Dinka", ["Ajiya (Storage)", "AI Services", "Sanarwa (Notifications)", "Database"])
M["cloud-i6"] = ("compare", "Multi-cloud vs Hybrid", "Multi-cloud", ["AWS + Google Cloud", "Guje wa dogaro da kamfani ɗaya"], "Hybrid Cloud", ["Cloud na Jama'a + Na Gida", "Misali: Banki"])
M["cloud-a1"] = ("hub", "Kubernetes", "Kubernetes", ["Container 1", "Container 2", "Container 3", "Auto-Restart"])
M["cloud-a2"] = ("flow", "Serverless Computing", ["Wani Aiki Ya Faru", "Code Ya Gudana (Lambda)", "An Kammala Aiki", "Ana Biyan Kuɗi Kadan Kacal"])
M["cloud-a3"] = ("hub", "Cloud Architecture", "Babban App", ["Microservice: Login", "Microservice: Payment", "Microservice: Search", "Load Balancer"])
M["cloud-a4"] = ("flow", "DevOps / CI-CD", ["Injiniya Ya Kara Code", "Atomatik Gwaji", "Atomatik Gina", "Saki ga Jama'a"])
M["cloud-a5"] = ("compare", "SQL vs NoSQL Database", "SQL (MySQL)", ["Tsari mai tsayayye", "Misali: Bayanan Banki"], "NoSQL (MongoDB)", ["Tsari mai sassauci", "Misali: Sakon Sada Zumunta"])
M["cloud-a6"] = ("iconrow", "Sana'o'in Cloud", [("briefcase","Cloud Engineer"),("briefcase","Cloud Architect"),("briefcase","DevOps Engineer")])

# ================= Cybersecurity (cyber-b1, cyber-b3 keep existing; cyber-a1 interactive) =================
M["cyber-b2"] = ("compare", "Kalmar Sirri", "Mai Rauni", ["\"password123\"", "Sunanka + Ranar Haihuwa"], "Mai Ƙarfi", ["\"Kt9#mPz2!qL\"", "10+ Haruffa, Alamomi, Lambobi"])
M["cyber-b4"] = ("iconrow", "Nau'ukan Malware", [("bug","Virus"),("mask","Trojan"),("lock","Ransomware")])
M["cyber-b5"] = ("flow", "Yadda Social Engineering Ke Aiki", ["Mai Laifi Ya Kira/Ya Aika Sako", "Ya Kirkiri Tsoro/Gaggawa", "Ka Amince Ba Tare da Tunani ba", "Ka Bayar da Bayani"])
M["cyber-b6"] = ("compare", "Wi-Fi na Gida vs na Jama'a", "Wi-Fi na Gida", ["Kalmar Sirri Mai Ƙarfi", "Aminci"], "Wi-Fi na Jama'a", ["Kowa Zai Iya Sa Ido", "Guji Aikin Banki"])
M["cyber-i1"] = ("iconrow", "Tsaro na Waya", [("lock","PIN/Fingerprint"),("magnifier","Find My Device"),("database","Backup")])
M["cyber-i2"] = ("iconrow", "Sirri a Sada Zumunta", [("lock","Privacy Settings"),("mask","Kar Ka Amince da Bakon Mutum")])
M["cyber-i3"] = ("iconrow", "Nau'ukan Zamba", [("phone","SIM Swap"),("money","Lottery Scam"),("shop","Zamba ta Sayayya")])
M["cyber-i4"] = ("cs", "Tsaro na Kuɗi ta Kan Layi", "Kai", "Banki/Shago", "Bukatar Sayayya (HTTPS)", "Ba Ka Taɓa Baiwa Kowa OTP Ba")
M["cyber-i5"] = ("flow", "Two-Factor Authentication", ["Ka Shigar da Kalmar Sirri", "+ Lambar SMS/App", "An Tabbatar da Kai", "Shiga Asusu"])
M["cyber-i6"] = ("iconrow", "Security Habits", [("checklist","Update Akai-akai"),("database","Backup"),("key","2FA Ko'ina")])
M["cyber-a2"] = ("compare", "Encryption", "Symmetric", ["Makulli Ɗaya", "Mai Sauri"], "Asymmetric", ["Makulli Biyu (Jama'a+Sirri)", "Ana Amfani a HTTPS"])
M["cyber-a3"] = ("compare", "Shared Responsibility (Cloud)", "Kamfanin Cloud", ["Tsaron Kayan Aiki"], "Kai (Mai Amfani)", ["Tsaron Bayananka da Saitunanka"])
M["cyber-a4"] = ("compare", "Ethical vs Black-Hat Hacking", "White-Hat (Ethical)", ["Bisa izini", "Domin gyara rauni"], "Black-Hat", ["Ba bisa izini ba", "Domin cutarwa"])
M["cyber-a5"] = ("flow", "Incident Response", ["Ganowa", "Killewa (Contain)", "Kawarwa", "Dawowa", "Koyo"])
M["cyber-a6"] = ("iconrow", "Kariya ga Karamin Kasuwanci", [("book","Horas da Ma'aikata"),("key","2FA"),("database","Backup")])

# ================= Data Science =================
M["ds-b1"] = ("hub", "Data Science", "Data Science", ["Kididdiga (Statistics)", "Programming (Python)", "Ilimin Kasuwanci"])
M["ds-b2"] = ("compare", "Nau'ukan Bayanai", "Structured", ["Teburin Excel", "Tsari Mai Tsayayye"], "Unstructured", ["Rubutun WhatsApp, Hoto", "Ba Tsayayyen Tsari Ba"])
M["ds-b3"] = ("iconrow", "Excel/Spreadsheets", [("chart","Pivot Table"),("database","Teburi")])
M["ds-b4"] = ("bars", "Data Visualization", [("Janairu", 0.4), ("Fabrairu", 0.7), ("Maris", 0.55), ("Afrilu", 0.9)])
M["ds-b5"] = ("bars", "Mean vs Median", [("Mean (N554,500)", 1.0), ("Median (N50,000)", 0.09)])
M["ds-b6"] = ("iconrow", "Python & Pandas", [("code","Python"),("database","Pandas"),("chart","Matplotlib")])
M["ds-i1"] = ("compare", "Data Cleaning", "Kafin Tsarkakewa", ["Bayanai Sun Bata (Missing)", "Kwafi (Duplicates)"], "Bayan Tsarkakewa", ["Cikakke da Daidai", "Shiryayye don Nazari"])
M["ds-i2"] = ("cs", "SQL", "Kai (Query)", "Database", "SELECT * FROM customers", "Jerin Sakamako")
M["ds-i3"] = ("flow", "ML don Data Science", ["Training Data", "Horas da Model", "Test Data", "Hasashe"])
M["ds-i4"] = ("bars", "A/B Testing", [("Zabi A (Ja)", 0.62), ("Zabi B (Kore)", 0.85)])
M["ds-i5"] = ("iconrow", "Dashboards", [("chart","Tableau"),("chart","Power BI")])
M["ds-i6"] = ("iconrow", "Data Ethics", [("lock","Izinin Mutane"),("compare","Bias a Bayanai")])
M["ds-a1"] = ("bars", "Big Data - 3 Vs", [("Volume", 0.9), ("Velocity", 0.75), ("Variety", 0.85)])
M["ds-a2"] = ("flow", "Predictive Modeling", ["Bayanan Baya", "Zaɓi Features", "Horas da Model", "Hasashen Makoma"])
M["ds-a3"] = ("stack", "Deep Learning", ["Output Layer - Amsa", "Hidden Layers - Sarrafawa", "Input Layer - Bayanai (Hoto/Sauti)"])
M["ds-a4"] = ("flow", "Data Engineering", ["Tattara Bayanai", "Tsaftace su Ta Atomatik", "Adana a Database", "Shirye don Nazari"])
M["ds-a5"] = ("cs", "Business Analytics", "Bayanan Kasuwanci", "Masanin Analytics", "Tambaya (Wace Yanki Ce Mafi Riba?)", "Shawara ga Manaja")
M["ds-a6"] = ("iconrow", "Sana'o'in Data Science", [("briefcase","Data Analyst"),("briefcase","Data Scientist"),("briefcase","Data Engineer")])

# ================= Mobile Development =================
M["mob-b1"] = ("cs", "Mobile App Development", "Wayarka", "Google Play / App Store", "Sauke App", "App Ya Sauka Ya Gudana")
M["mob-b2"] = ("compare", "Android vs iOS", "Android", ["Google ce ta kirkira", "Kotlin/Java", "Samsung, Tecno, Infinix"], "iOS", ["Apple ce ta kirkira", "Swift", "iPhone Kadai"])
M["mob-b3"] = ("compare", "Native vs Cross-Platform", "Native", ["Kotlin/Swift daban-daban", "Mafi Kyawun Aiki"], "Cross-Platform", ["Code Ɗaya (Flutter/RN)", "Sauri da Araha wajen Ginawa"])
M["mob-b4"] = ("compare", "UI vs UX", "UI (User Interface)", ["Launi, Maballi, Tsari"], "UX (User Experience)", ["Sauki wajen Amfani", "Gogewar Mai Amfani"])
M["mob-b5"] = ("iconrow", "Kayan Aiki", [("code","Android Studio"),("code","Xcode")])
M["mob-b6"] = ("flow", "Gina App na Farko", ["Kirkiri Project", "Rubuta Hello World", "Kara Maballi/Fom", "Gwada a Emulator"])
M["mob-i1"] = ("compare", "Flutter vs React Native", "Flutter (Google)", ["Yaren Dart", "Custom Rendering"], "React Native (Meta)", ["Yaren JavaScript", "Amfani da Native Components"])
M["mob-i2"] = ("compare", "SharedPreferences vs SQLite", "SharedPreferences", ["Karamin Bayani (Saitunan Mai Amfani)"], "SQLite/Room", ["Manyan Bayanai Masu Tsari"])
M["mob-i3"] = ("cs", "APIs/Backend a Mobile", "App", "Backend Server", "Bukata (REST API)", "Amsa (JSON)")
M["mob-i4"] = ("compare", "Emulator vs Real Device", "Emulator", ["Sauri, a Kwamfuta"], "Wayar Ainihi", ["Gwada Baturi/Sensor na Gaske"])
M["mob-i5"] = ("flow", "Buga App a Store", ["Kirkiri Asusun Developer", "Shirya Bayani/Hotuna", "Google/Apple Ya Bincika", "App Ya Buga"])
M["mob-i6"] = ("iconrow", "Monetization", [("chat","Talla (Ads)"),("money","In-App Purchase"),("key","Subscription")])
M["mob-a1"] = ("cs", "Push Notifications", "Server", "Wayarka", "Sabon Sako", "Sanarwa (Ko App Bai Bude Ba)")
M["mob-a2"] = ("iconrow", "Tsaron Mobile App", [("lock","HTTPS Koyaushe"),("key","Ɓoye API Keys"),("shield","Certificate Pinning")])
M["mob-a3"] = ("bars", "Performance - Kafin/Bayan Optimization", [("Kafin (Sakan 5)", 1.0), ("Bayan Optimization (Sakan 1.5)", 0.3)])
M["mob-a4"] = ("flow", "CI/CD Mobile (Fastlane)", ["Kara Code", "Atomatik Gina App", "Atomatik Gwaji", "Saki zuwa Store"])
M["mob-a5"] = ("hub", "Wearables/IoT", "Wayarka", ["Apple Watch", "Sensor na Zuciya", "Firji Mai Wayo", "BLE (Bluetooth)"])
M["mob-a6"] = ("iconrow", "Sana'o'in Mobile", [("briefcase","Android Developer"),("briefcase","iOS Developer"),("briefcase","Mobile Engineer")])

# ================= Networking (net-a1 interactive) =================
M["net-b1"] = ("hub", "Cibiyar Sadarwa", "Wi-Fi Router", ["Wayar Hannu", "Kwamfuta", "Printer", "Smart TV"])
M["net-b2"] = ("cs", "IP Address", "Wayarka", "Wata Na'ura", "Adireshi: 192.168.1.5", "Bayanai Sun Isa Daidai")
M["net-b3"] = ("hub", "Router, Switch, Modem", "Cibiyar Sadarwar Gida", ["Modem: Canza Siginar Intanet", "Router: Rarraba Wi-Fi", "Switch: Hada Na'urori da Waya"])
M["net-b4"] = ("compare", "Wi-Fi vs Ethernet", "Wi-Fi", ["Wireless, Mai Sauki", "Wani Lokaci a Hankali"], "Ethernet", ["Ta Kebul", "Mai Sauri da Tabbaci"])
M["net-b5"] = ("flow", "Yadda Intanet Ke Aiki", ["Ka Bude Shafi", "Bukata Ta Bi Kebul", "Ta Isa Sabar", "Sabar Ta Aika Amsa"])
M["net-b6"] = ("flow", "Matsalolin Network", ["Wi-Fi Mai Jinkiri?", "Duba Nisa daga Router", "Sake Kunna Router (Restart)", "Matsalar Ta Warware"])
M["net-i1"] = ("stack", "OSI Model - 7 Layers", ["7. Application", "6. Presentation", "5. Session", "4. Transport", "3. Network", "2. Data Link", "1. Physical"])
M["net-i2"] = ("compare", "TCP vs UDP", "TCP", ["Tabbatar da Isarwa", "A Hankali Kadan"], "UDP", ["Ba Ya Tabbatarwa", "Mai Sauri (Streaming)"])
M["net-i3"] = ("flow", "Yadda DNS Ke Aiki", ["Ka Rubuta google.com", "Browser Ya Tambayi DNS", "DNS Ta Bada IP Address", "Browser Ya Hade da Server"])
M["net-i4"] = ("compare", "Bandwidth vs Latency", "Bandwidth", ["Fadin Hanya", "Adadin Bayanai a Lokaci Guda"], "Latency", ["Tsawon Hanya", "Jinkirin Isar Bayani"])
M["net-i5"] = ("cs", "VPN", "Wayarka", "Sabar VPN sannan Intanet", "Bayanai an Ɓoye (Encrypted Tunnel)", "Adireshin IP na Wata Ƙasa")
M["net-i6"] = ("iconrow", "Tsaron Network na Asali", [("key","Canza Kalmar Sirri"),("shield","WPA3"),("mask","Ɓoye SSID")])
M["net-a2"] = ("hub", "Load Balancing", "Load Balancer", ["Server 1", "Server 2", "Server 3"])
M["net-a3"] = ("compare", "HTTP/2 vs HTTP/3 (QUIC)", "HTTP/2", ["Yana Amfani da TCP"], "HTTP/3", ["Yana Amfani da QUIC", "Mai Sauri, Jimre da Sauya Network"])
M["net-a4"] = ("compare", "SDN", "Control Plane", ["Yanke Shawara (Software)"], "Data Plane", ["Aikatawa (Na'urori)"])
M["net-a5"] = ("bars", "4G vs 5G", [("4G Sauri", 0.35), ("5G Sauri", 1.0)])
M["net-a6"] = ("iconrow", "Sana'o'in Networking", [("briefcase","Network Administrator"),("briefcase","Network Engineer"),("briefcase","Security Specialist")])

# ================= Programming =================
M["prog-b1"] = ("cs", "Programming", "Kai (Mai Shirye-shirye)", "Kwamfuta", "Umarni (Code)", "Kwamfuta Ta Aikata")
M["prog-b2"] = ("iconrow", "Variables & Data Types", [("database","Integer (25)"),("chat","String (\"Sannu\")"),("compare","Boolean (True/False)")])
M["prog-b3"] = ("compare", "If / Else", "if age >= 18", ["Za Ka Iya Jefa Kuri'a"], "else", ["Ba Za Ka Iya Ba Tukuna"])
M["prog-b4"] = ("compare", "For Loop vs While Loop", "For Loop", ["Adadin Sau da Aka Sani", "Misali: 1 zuwa 100"], "While Loop", ["Har Sai Sharadi Ya Zama Karya"])
M["prog-b5"] = ("flow", "Functions", ["Kirkiri Function (addNumbers)", "Kira Ta a Wurare Daban-daban", "Sake Amfani Ba Tare da Sake Rubutu ba"])
M["prog-b6"] = ("iconrow", "Zaben Yaren Farko", [("code","Python - Sauki"),("code","JavaScript - Don Web")])
M["prog-i1"] = ("tree", "Array/List", "Jerin Sunaye", ["Index 0: Amina", "Index 1: Bello", "Index 2: Aisha"])
M["prog-i2"] = ("hub", "Object-Oriented Programming", "Class: Car", ["Property: color", "Property: speed", "Method: accelerate()", "Method: brake()"])
M["prog-i3"] = ("flow", "Git & Version Control", ["Gyara Code", "git commit", "git push", "Ana Ganin Tarihi a GitHub"])
M["prog-i4"] = ("flow", "Debugging", ["Kuskure Ya Faru (Bug)", "Amfani da Print/Debugger", "Gano Inda Matsalar Take", "Gyara Code"])
M["prog-i5"] = ("cs", "APIs a Programming", "Code Dinka", "Wata Software (Weather API)", "Bukata", "Amsa (JSON)")
M["prog-i6"] = ("flow", "Algorithm", ["Matsala: Nemi Mafi Girman Lamba", "Bincika Lamba ta Farko", "Kwatanta da Na Gaba", "Mayar da Mafi Girma"])
M["prog-a1"] = ("tree", "Tree Data Structure", "Root (Saiwa)", ["Reshe A", "Reshe B", "Reshe C"])
M["prog-a2"] = ("iconrow", "Design Patterns", [("gear","Singleton"),("chat","Observer")])
M["prog-a3"] = ("flow", "Testing", ["Rubuta Unit Test", "Kara Sabon Code", "Atomatik Gwaji Ya Gudana", "An Tabbatar Babu Karya"])
M["prog-a4"] = ("compare", "Sequential vs Concurrent", "Sequential", ["Aiki Ɗaya Bayan Ɗaya"], "Concurrent", ["Ayyuka da Yawa Lokaci Guda"])
M["prog-a5"] = ("compare", "Secure vs Insecure Code", "Insecure", ["Babu Input Validation", "SQL Injection Na Iya Faruwa"], "Secure", ["Input Validation", "Parameterized Queries"])
M["prog-a6"] = ("iconrow", "Sana'o'in Software", [("code","Frontend Developer"),("database","Backend Developer"),("briefcase","Full-Stack Developer")])

# ================= Web Development =================
M["web-b1"] = ("cs", "Yadda Web Ke Aiki", "Browser (Chrome)", "Sabar (Server)", "Bukata (URL)", "HTML, CSS, JavaScript")
M["web-b2"] = ("iconrow", "HTML Tags", [("code","<h1> Take"),("code","<p> Sakin Layi"),("code","<img> Hoto")])
M["web-b3"] = ("compare", "Kafin CSS vs Bayan CSS", "Kafin CSS", ["Rubutu Baki da Fari Kawai", "Babu Tsari"], "Bayan CSS", ["Launi da Kyan Gani", "Tsari Mai Kyau"])
M["web-b4"] = ("flow", "JavaScript Ke Rayar da Shafi", ["Ka Danna Maballi", "JavaScript Ta Gudana", "Shafi Ya Canza", "Ba Tare da Sake Loda Shafi Gaba Ɗaya ba"])
M["web-b5"] = ("flow", "Browser Yana Fassara Code", ["Karban HTML/CSS/JS", "Rendering Engine", "Fassara zuwa Hoto", "Nuna Shafi ga Mai Amfani"])
M["web-b6"] = ("stack", "Shafi na Farko", ["JavaScript - Rayuwa", "CSS - Kyan Gani", "HTML (index.html) - Tsari"])
M["web-i1"] = ("compare", "Responsive Design", "Babbar Kwamfuta", ["Menu a Gefe", "Sarari Mai Yawa"], "Wayar Hannu", ["Hamburger Menu", "Karamin Sarari"])
M["web-i2"] = ("iconrow", "Frontend Frameworks", [("code","React (Facebook)"),("code","Vue")])
M["web-i3"] = ("cs", "Backend Development", "Frontend (Mai Amfani)", "Backend (Server + Database)", "Shigar da Kalmar Sirri", "Tantancewa da Amsa")
M["web-i4"] = ("compare", "SQL vs NoSQL a Web", "MySQL/PostgreSQL", ["Tsari Mai Tsayayye"], "MongoDB", ["Tsari Mai Sassauci"])
M["web-i5"] = ("cs", "REST APIs", "Frontend", "Backend", "Bukata (Request)", "Amsa (JSON)")
M["web-i6"] = ("flow", "Hosting/Deployment", ["Rubuta Code", "CI/CD Ta Gwada", "Loda zuwa Netlify/Vercel", "Shafi Ya Kasance a Intanet"])
M["web-a1"] = ("stack", "Full-Stack Architecture", ["Frontend - React", "Backend - Node.js", "Database - PostgreSQL"])
M["web-a2"] = ("bars", "Web Performance - Kafin/Bayan", [("Kafin (Sakan 6)", 1.0), ("Bayan Optimization (Sakan 2)", 0.33)])
M["web-a3"] = ("iconrow", "Web Security", [("bug","XSS - Code Mai Hatsari"),("mask","CSRF - Aikin Karya")])
M["web-a4"] = ("iconrow", "Progressive Web App (PWA)", [("wifi","Aiki Ko Babu Intanet"),("chat","Push Notifications"),("phone","Sanya a Allon Farko")])
M["web-a5"] = ("compare", "SSR vs CSR", "Server-Side Rendering", ["Sabar Ta Gina Shafi", "Sauri wajen Gani"], "Client-Side Rendering", ["Browser Ya Gina Shafi", "JavaScript Ta Fara Gudana"])
M["web-a6"] = ("iconrow", "Sana'o'in Web Dev", [("code","Frontend Developer"),("database","Backend Developer"),("compare","UI/UX Designer")])


def dispatch(path, color, template, title, *args):
    if template == "card":
        render_lesson_card(path, "gear", title, args[0], color)
    elif template == "stack":
        render_stack(path, title, args[0], color)
    elif template == "flow":
        render_flow(path, title, args[0], color)
    elif template == "compare":
        render_compare(path, title, args[0], args[1], args[2], args[3], color)
    elif template == "hub":
        render_hub(path, title, args[0], args[1], color)
    elif template == "cs":
        render_client_server(path, title, args[0], args[1], args[2], args[3], color)
    elif template == "iconrow":
        render_icon_row(path, title, args[0], color)
    elif template == "tree":
        render_tree(path, title, args[0], args[1], color)
    elif template == "bars":
        render_bars(path, title, args[0], color)
    else:
        raise ValueError(f"unknown template {template}")


def main():
    courses_dir = os.path.join(os.path.dirname(__file__), "..", "app", "src", "main", "assets", "courses")
    images_dir = os.path.join(os.path.dirname(__file__), "..", "app", "src", "main", "assets", "images")

    generated = 0
    missing = []
    for fpath in sorted(glob.glob(f"{courses_dir}/*.json")):
        course_key = fpath.split(os.sep)[-1].replace(".json", "")
        color = COLORS[course_key]
        d = json.load(open(fpath, encoding="utf-8"))
        changed = False
        for lvl in d["levels"]:
            for lesson in lvl["lessons"]:
                lid = lesson["id"]
                if lid not in M:
                    if lesson.get("diagram") is None:
                        missing.append(lid)
                    continue
                template, title, *args = M[lid]
                asset_name = f"{lid}.png"
                dispatch(f"{images_dir}/{asset_name}", color, template, title, *args)
                lesson["diagram"] = {
                    "type": "image",
                    "asset": asset_name,
                    "caption": lesson["summary"]
                }
                generated += 1
                changed = True
        if changed:
            with open(fpath, "w", encoding="utf-8") as out:
                json.dump(d, out, ensure_ascii=False, indent=2)

    print(f"Generated/updated {generated} lesson diagrams.")
    if missing:
        print("Lessons with NO diagram and NOT in manifest:", missing)

if __name__ == "__main__":
    main()
