# Generates 4 supplementary diagrams per lesson for all courses
# Uses raw string insertion to preserve Hausa Unicode characters

$targetDir = 'C:\Users\LAB\Desktop\Hausa Tech\TechHausa\TechHausa\app\src\main\assets\courses'
$zipDir = 'C:\Users\LAB\AppData\Local\Temp\opencode\newcheck2\HausaTech-main\app\src\main\assets\courses'

# Restore from zip first
Copy-Item "$zipDir\*.json" $targetDir -Force
Write-Output "Restored JSON files from zip"

function Insert-SuppDiag($filePath, $lessonId, $jsonSnippet) {
    $raw = [System.IO.File]::ReadAllText($filePath, [System.Text.Encoding]::UTF8)
    $idSearch = '"id": "' + $lessonId + '"'
    $idPos = $raw.IndexOf($idSearch)
    if ($idPos -lt 0) { return }
    $quizPos = $raw.IndexOf('"quiz":', $idPos)
    if ($quizPos -lt 0) { return }
    $indent = "            "
    $insert = $indent + '"supplementaryDiagrams": [' + $jsonSnippet + '],' + "`n"
    $raw = $raw.Insert($quizPos, $insert)
    [System.IO.File]::WriteAllText($filePath, $raw, [System.Text.Encoding]::UTF8)
}

$courseColors = @{
    'ai' = 'AI'
    'cloud_computing' = 'Cloud'
    'cybersecurity' = 'Cybersecurity'
    'data_science' = 'Data Science'
    'mobile_development' = 'Mobile'
    'networking' = 'Networking'
    'programming' = 'Programming'
    'web_development' = 'Web Development'
}

# ============================================================
# AI COURSE - 18 lessons
# ============================================================
$f = "$targetDir\ai.json"
$inserted = 0

# ai-b1: Menene AI
Insert-SuppDiag $f 'ai-b1' @"
{"type":"flow","title":"Yadda AI Ke","items":["Tattara Bayanai","Nuna Misalai","Gano Sifofi","Fuskantar Sabbin"],"caption":"AI tana koyo daga bayanai"},{"type":"compare","title":"Narrow AI vs AGI","leftTitle":"Narrow AI","leftItems":["Aiki ɗaya kacal","Yana wanzuwa a yau","Misali: Siri, Alexa"],"rightTitle":"AGI","rightItems":["Kowane fanni kamar mutum","Har yanzu ra'ayi ne","Ba a samu ba tukuna"]},{"type":"hub","title":"AI a Rayuwar Yau","center":"AI","satellites":["Google Maps","Netflix","Face Unlock","Siri","ChatGPT"]},{"type":"stack","title":"Katunan AI","items":["Aikace-aikace (ChatGPT, Siri)","Frameworks (TensorFlow, PyTorch)","Machine Learning","Bayanai (Data)"]}
"@

# ai-b2: AI a Kayan Aikin
Insert-SuppDiag $f 'ai-b2' @"
{"type":"flow","title":"Yadda AI Ke","items":["Tattara Bayanai Masu Yawa","Nuna Misalai da Amsoshi","AI Ta Gano Sifofi","AI ta fi kyau"],"caption":"AI tana koyo daga bayanai"},{"type":"compare","title":"AI da Masu Sana'a","leftTitle":"Masu Sana'a","leftItems":["Rubutu","Cin Abinci","Sana'o'in Yau"],"rightTitle":"AI","rightItems":["Gudu Cikin Sauri","Rike da Ayyuka","Saukar da Ma'aikata"]},{"type":"hub","title":"AI a Kasuwanci","center":"Kasuwanci","satellites":["Kula da Abokan Ciniki","Saman Bayanai","Harshen Juna","Shirye-shirye"]},{"type":"stack","title":"AI a Kasuwanci","items":["Gida - Kasuwanci","CI/CD - Gwaji","Frameworks - AI","Data - Bayanai"]}
"@

# ai-b3: Yadda AI Ke Aiki
Insert-SuppDiag $f 'ai-b3' @"
{"type":"flow","title":"Yadda AI Ke","items":["Karban Bayanai","Fassara da Nuna Bayani","Yi Aiki daidai","Bayar da Amsa"],"caption":"AI yana aiki ta hanyar fassarar bayanai"},{"type":"compare","title":"AI da Kwamfuta","leftTitle":"Kwamfuta","leftItems":["Ta karɓi umarni","Ba ta iya koyo","Tana aiki da shirye-shirye"],"rightTitle":"AI","rightItems":["Tana koyo daga bayanai","Tana fahimtar magana","Tana yinumbnailan mutum"]},{"type":"hub","title":"AI a Kasuwanci","center":"AI","satellites":["Talla","Kula da Abokan Ciniki","Saman Bayanai","Harshen Juna"]},{"type":"stack","title":"Tsarin AI","items":["Na'urori masu wayo","AI","Bayanai","Shirye-shirye"]}
"@

# ai-b4: Yadda AI Ke Koyo
Insert-SuppDiag $f 'ai-b4' @"
{"type":"flow","title":"Matakai na Koyo","items":["Tattara Bayanai","Shirya Bayanai","Horas da Model","Gwada da Inganta"],"caption":"AI tana koyo ta hanyar matakan guda 4"},{"type":"compare","title":"Supervised vs Unsupervised","leftTitle":"Supervised","leftItems":["Bayanai + Amsoshi","Misali: Hoto + Suna","Mafi sauƙi"],"rightTitle":"Unsupervised","rightItems":["Bayanai kadai","AI ta gano sifofi","Mafi rikici"]},{"type":"hub","title":"AI a Wasu Fannai","center":"AI","satellites":["Wasan Kwamfuta","Lafiyar Jiki","Tattaunawa","Hotuna"]},{"type":"stack","title":"AI Stack","items":["Aikace-aikace","Frameworks","Machine Learning","Data"]}
"@

# ai-b5: Misalan AI
Insert-SuppDiag $f 'ai-b5' @"
{"type":"flow","title":"Gina Chatbot","items":["Zaɓin Lafazi","Shirya Bayanai","Gina Tsarin Tattaunawa","Gwada da Kunnawa"],"caption":"Gina chatbot yana buƙatar matakan guda 4"},{"type":"compare","title":"ChatGPT vs Gemini","leftTitle":"ChatGPT","leftItems":["OpenAI ce ta samar","Mafi shahara","Yana da API"],"rightTitle":"Gemini","rightItems":["Google ce ta samar","Yana haɗawa da Google","Sabon fasali"]},{"type":"hub","title":"AI Chatbots","center":"Chatbots","satellites":["ChatGPT","Claude","Gemini","Copilot"]},{"type":"stack","title":"AI Chatbot Stack","items":["User Interface","NLP Engine","Language Model","Training Data"]}
"@

# ai-b6: Iyakokin AI
Insert-SuppDiag $f 'ai-b6' @"
{"type":"flow","title":"Matsalolin AI","items":["Rashin Daidaito","Ƙarancin Fahimta","Matsalar Sirri","Yiwuwar Rikici"],"caption":"AI tana da iyakoki masu yawa"},{"type":"compare","title":"AI da Mutum","leftTitle":"Mutum","leftItems":["Yana da hankali","Yana iya yin kuskure","Yana da baƙin ciki"],"rightTitle":"AI","rightItems":["Ba ta da hankali","Ba ta iya yin kuskure","Ba ta da baƙin ciki"]},{"type":"hub","title":"Iyakokin AI","center":"AI","satellites":["Hallucination","Bias","Privacy","Deepfakes"]},{"type":"stack","title":"AI Ethics Stack","items":["Dokoki","Ethics","Tsaro","Bayanai"]}
"@

# ai-i1: Narrow AI vs AGI
Insert-SuppDiag $f 'ai-i1' @"
{"type":"flow","title":"Haɓaka zuwa AGI","items":["Narrow AI (Yau)","Machine Learning","Deep Learning","AGI (Makoma)"],"caption":"AGI yana nufin AI mai daidaito kamar mutum"},{"type":"compare","title":"Narrow AI vs AGI","leftTitle":"Narrow AI","leftItems":["Aiki ɗaya","Yana wanzuwa a yau","Siri, Alexa, ChatGPT"],"rightTitle":"AGI","rightItems":["Kowane fanni","Har yanzu ra'ayi","Ba a samu ba"]},{"type":"hub","title":"Nau'ukan AI","center":"AI","satellites":["Narrow AI","Machine Learning","Deep Learning","AGI"]},{"type":"stack","title":"Tsarin AGI","items":["Aikace-aikace","Cognitive Architecture","Machine Learning","General Intelligence"]}
"@

# ai-i2: Machine Learning
Insert-SuppDiag $f 'ai-i2' @"
{"type":"flow","title":"ML Pipeline","items":["Tattara Bayanai","Shirya Bayanai","Horas da Model","Gwada da Yi Amfani"],"caption":"Machine Learning yana buƙatar bayanai masu yawa"},{"type":"compare","title":"Supervised vs Unsupervised","leftTitle":"Supervised","leftItems":["Bayanai + Amsoshi","Classification","Regression"],"rightTitle":"Unsupervised","rightItems":["Bayanai kadai","Clustering","Anomaly Detection"]},{"type":"hub","title":"ML a Kasuwanci","center":"ML","satellites":["Prediction","Classification","Clustering","Recommendation"]},{"type":"stack","title":"ML Stack","items":["Deployment","Model Training","Feature Engineering","Data Collection"]}
"@

# ai-i3: Kayan Aikin AI
Insert-SuppDiag $f 'ai-i3' @"
{"type":"flow","title":"Amfani da AI Tools","items":["Zaɓin Kayan Aiki","Rubuta Tambaya","Duba Amsar","Yi Amfani da Ita"],"caption":"Kayan aiki da yawa suna da AI a cikinsu"},{"type":"compare","title":"Yaren Rubutu","leftTitle":"Gajeren Rubutu","leftItems":["Gajere","Mara Bayani","Mafi Sauki"],"rightTitle":"Cikakken Rubutu","rightItems":["Tsayi","Mai Bayani","Mafi Kyau"]},{"type":"hub","title":"Kayan Aikin AI","center":"AI Tools","satellites":["ChatGPT","Midjourney","Grammarly","Copilot"]},{"type":"stack","title":"AI Tool Stack","items":["User Interface","NLP/Generation","Model","Data"]}
"@

# ai-i4: AI Wajen Aiki
Insert-SuppDiag $f 'ai-i4' @"
{"type":"flow","title":"AI a Aikin Yau","items":["Gano Matsalar","Zaɓin Kayan Aiki","Rubuta Tambaya","Yi Amfani da Amsa"],"caption":"AI tana taimaka wa a ayyuka da yawa"},{"type":"compare","title":"AI da Mutum a Aiki","leftTitle":"Mutum","leftItems":["Yana hanzari","Yana da iyakoki","Yana da hankali"],"rightTitle":"AI","rightItems":["Mai sauri","Ba ta da iyakoki","Ba ta da hankali"]},{"type":"hub","title":"AI a Aikin","center":"AI","satellites":["Rubutu","Takaitawa","Nazarin Bayanai","Shirye-shirye"]},{"type":"stack","title":"AI in Work","items":["Automation","Decision Support","Content Generation","Data Analysis"]}
"@

# ai-i5: AI Ethics
Insert-SuppDiag $f 'ai-i5' @"
{"type":"flow","title":"Matsalolin Ethics","items":["Gano Matsalar","Nazarin Tasiri","Yanke Shawa","Sake Dubawa"],"caption":"Ethics yana buƙatar tunani mai zurfi"},{"type":"compare","title":"AI da Adalci","leftTitle":"AI mai Adalci","leftItems":["Ba ta da son zuciya","Yana amfani da bayanai","Mai gaskiya"],"rightTitle":"AI mara Adalci","rightItems":["Yana da son zuciya","Yana rufe bayanai","Mai ɓatanci"]},{"type":"hub","title":"AI Ethics","center":"Ethics","satellites":["Bias","Privacy","Deepfakes","Transparency"]},{"type":"stack","title":"Ethics Framework","items":["Policies","Audit","Monitoring","Accountability"]}
"@

# ai-i6: AI da Ayyukan Yi
Insert-SuppDiag $f 'ai-i6' @"
{"type":"flow","title":"Sabbin Ayyuka","items":["Gano Fasaha","Koyon Fasaha","Gina Kayan Aiki","Shirya Yarjejeniya"],"caption":"AI yana samar da sabbin ayyuka"},{"type":"compare","title":"Ayyukan da ke Raguwa vs Sabbin","leftTitle":"Ayyuka Masu Raguwa","leftItems":["Ma'aikatan shirye-shirye","Masu shigar da bayanai","Ma'aikatan tallace-tallace"],"rightTitle":"Sabbin Ayyuka","rightItems":["Prompt Engineer","AI Trainer","AI Ethics Officer"]},{"type":"hub","title":"Sana'o'in AI","center":"AI Jobs","satellites":["Prompt Engineer","ML Engineer","Data Scientist","AI Ethics"]},{"type":"stack","title":"AI Career Path","items":["AI Ethics","Applied AI","ML Engineering","Research"]}
"@

# ai-a2: Large Language Models
Insert-SuppDiag $f 'ai-a2' @"
{"type":"flow","title":"Yadda LLM Ke","items":["Karban Rubutunka","Duba Mahallin","Hasashen Kalma","Maimaita Har Amsa"],"caption":"LLM yana gina jimla ta hanyar hasashen kalma"},{"type":"compare","title":"LLM da Na Baya","leftTitle":"Na Baya","leftItems":["Tsayi guda ɗaya","Babu mahallin tattaunawa","Mafi sauƙi"],"rightTitle":"LLM","rightItems":["Tsawo da yawa","Tana da mahallin tattaunawa","Mafi rikici"]},{"type":"hub","title":"LLMs","center":"LLM","satellites":["GPT-4","Claude","Gemini","Llama"]},{"type":"stack","title":"LLM Stack","items":["Fine-tuning","Transformer Architecture","Pre-training Data","Tokenization"]}
"@

# ai-a3: Generative AI
Insert-SuppDiag $f 'ai-a3' @"
{"type":"flow","title":"Yadda GenAI Ke","items":["Karban Umarni","Fahimtar Abin da ake Nufi","Samar da Abubuwa","Nuna Sakamako"],"caption":"GenAI yana samar da abubuwa sabuwa daga bayanai"},{"type":"compare","title":"GenAI da AI na Baya","leftTitle":"AI na Baya","leftItems":["Gano abubuwa","Rarraba","Hasashen lambobi"],"rightTitle":"GenAI","rightItems":["Samar da rubutu","Samar da hotuna","Samar da bidiyo"]},{"type":"hub","title":"GenAI","center":"Generative AI","satellites":["Text","Images","Audio","Video"]},{"type":"stack","title":"GenAI Stack","items":["Applications (ChatGPT)","Models (GPT, DALL-E)","Training","Data"]}
"@

# ai-a4: Fine-tuning vs RAG
Insert-SuppDiag $f 'ai-a4' @"
{"type":"flow","title":"Zaɓen Hanya","items":["Gano Manufa","Duba Bayanai","Zaɓi Hanya","Gwada da Inganta"],"caption":"Zaɓen hanya yana dogara da manufa da bayanai"},{"type":"compare","title":"Fine-tuning vs RAG","leftTitle":"Fine-tuning","leftItems":["Canza model","Horas da sabuwar","Buƙatar GPU mai ƙarfi"],"rightTitle":"RAG","rightItems":["Kara bayanai","Ba a canza model","Mafi araha"]},{"type":"hub","title":"RAG vs Fine-tuning","center":"Model","satellites":["Fine-tuning","RAG","Prompt Engineering","Retrieval"]},{"type":"stack","title":"RAG Stack","items":["Retrieval","Augmentation","Generation","Evaluation"]}
"@

# ai-a5: AI Agents
Insert-SuppDiag $f 'ai-a5' @"
{"type":"flow","title":"Yadda AI Agent Ke","items":["An Bada Manufa","Ya Tsara Matakai","Ya Aiwatar","Ya Duba Sakamako"],"caption":"AI Agent yana aiki da kanta domin cimma manufa"},{"type":"compare","title":"AI Agent da Chatbot","leftTitle":"Chatbot","leftItems":["Amsa tambaya","Ba ya yi wani aiki","Mafi sauƙi"],"rightTitle":"AI Agent","rightItems":["Yana aiwatar da aiki","Yana yin shawarwari","Mafi rikici"]},{"type":"hub","title":"AI Agents","center":"Agent","satellites":["Planning","Tool Use","Memory","Reasoning"]},{"type":"stack","title":"Agent Stack","items":["Action Layer","Planning","LLM Core","Memory"]}
"@

# ai-a6: Sana'o'in AI
Insert-SuppDiag $f 'ai-a6' @"
{"type":"flow","title":"Hanyar zuwa Sana'o'in AI","items":["Koyon Fasaha","Gina Ayyuka","Shirya Portfolio","Neman Aiki"],"caption":"Sana'o'in AI suna buƙatar fasaha da iƙwar wrestler"},{"type":"compare","title":"Sana'o'in AI","leftTitle":"Masu Rubutu","leftItems":["Prompt Engineer","Technical Writer","Content Creator"],"rightTitle":"Masu Fasaha","rightItems":["ML Engineer","Data Scientist","AI Researcher"]},{"type":"hub","title":"Sana'o'in AI","center":"AI Careers","satellites":["Prompt Engineer","ML Engineer","Data Scientist","AI Product Manager"]},{"type":"stack","title":"AI Career Ladder","items":["AI Ethics Officer","AI Product Manager","ML Engineer","Research Scientist"]}
"@

Write-Output "AI course: $inserted lessons updated"

# ============================================================
# CLOUD COMPUTING - 18 lessons
# ============================================================
$f = "$targetDir\cloud_computing.json"

Insert-SuppDiag $f 'cloud-b1' @"
{"type":"flow","title":"Yadda Cloud Ke","items":["Kai ka sami Sabar","Ka loda Fayiloli","Kai ka haɗa Na'urori","Kai ka yi Amfani"],"caption":"Cloud yana ba ka damar samun kayan aiki ko'ina"},{"type":"compare","title":"Cloud da Kwamfuta","leftTitle":"Kwamfuta ta Gida","leftItems":["Kana rike da shi","Kana buƙatar kulawa","Mafi ƙarfi"],"rightTitle":"Cloud","rightItems":["Kana amfani da na wani","Ba ka buƙatar kulawa","Mafi sauri"]},{"type":"hub","title":"Cloud Providers","center":"Cloud","satellites":["AWS","Azure","Google Cloud","Alibaba"]},{"type":"stack","title":"Cloud Stack","items":["SaaS (Gmail)","PaaS (Heroku)","IaaS (EC2)","Physical Hardware"]}
"@

Insert-SuppDiag $f 'cloud-b2' @"
{"type":"flow","title":"Zaɓen Sabis","items":["Gano Bukatar Ka","Zaɓi Irin Sabis","Bude Asusu","Far da Amfani"],"caption":"Zaɓen sabis yana dogara da bukatun ka"},{"type":"compare","title":"IaaS vs PaaS vs SaaS","leftTitle":"IaaS","leftItems":["Kwamfuta + Ajiya","Mafi ƙarfin sarrafa","Misali: EC2"],"rightTitle":"PaaS","rightItems":["Dandali don gina app","Mai sauƙin amfani","Misali: Heroku"]},{"type":"hub","title":"Cloud Services","center":"Sabis","satellites":["Compute","Storage","Database","Networking"]},{"type":"stack","title":"Cloud Service Models","items":["SaaS","PaaS","IaaS","On-Premise"]}
"@

Insert-SuppDiag $f 'cloud-b3' @"
{"type":"flow","title":"Zaɓen Cloud","items":["Nazarin bukatun ka","Yanƙididdigen kuɗi","Gwada kyauta","Yanke shawa"],"caption":"Zaɓen cloud yana buƙatar nazarin bukatun ka"},{"type":"compare","title":"AWS vs Azure vs GCP","leftTitle":"AWS","leftItems":["Mafi yawa amfani","Mafi zurfin fasali","Mafi girman kasuwa"],"rightTitle":"Azure","rightItems":["Mafi kyau ga Microsoft","Yana haɗawa da Office 365","Mafi kyau ga ƙwararru"]},{"type":"hub","title":"Cloud Providers","center":"Providers","satellites":["AWS","Azure","Google Cloud","IBM"]},{"type":"stack","title":"Cloud Market","items":["Google Cloud","Microsoft Azure","AWS (Amazon)"]}
"@

Insert-SuppDiag $f 'cloud-b4' @"
{"type":"flow","title":"Amfanin Cloud","items":["Rage Kuɗin Farawa","Girma a Hankali","Sauki wajen Amfani","Aminci da Tabbaci"],"caption":"Cloud yana ba da fa'idodi da yawa"},{"type":"compare","title":"Cloud da Gida","leftTitle":"Na Gida","leftItems":["Kuɗin farawa mai girma","Dole a sanya manufa","Mafi aminci"],"rightTitle":"Cloud","rightItems":["Ba a buƙatar kuɗin farawa","Girma a hankali","Mafi sauri"]},{"type":"hub","title":"Fa'idodin Cloud","center":"Cloud","satellites":["Rage Kuɗi","Sauki","Girma","Aminci"]},{"type":"stack","title":"Cloud Benefits","items":["Scalability","Cost Savings","Reliability","Global Reach"]}
"@

Insert-SuppDiag $f 'cloud-b5' @"
{"type":"flow","title":"Amfani da Cloud Storage","items":["Bude Asusu","Loda Fayiloli","Raba da Abokai","Saita Aminci"],"caption":"Cloud Storage yana adana bayananku a aminci"},{"type":"compare","title":"Google Drive vs Dropbox","leftTitle":"Google Drive","leftItems":["Kyauta 15GB","Yana haɗawa da Gmail","Mafi sauƙi"],"rightTitle":"Dropbox","rightItems":["Kyauta 2GB","Mafi kyau ga ƙwararru","Mafi aminci"]},{"type":"hub","title":"Cloud Storage","center":"Storage","satellites":["Google Drive","Dropbox","OneDrive","iCloud"]},{"type":"stack","title":"Storage Stack","items":["Sync","Backup","Sharing","Security"]}
"@

Insert-SuppDiag $f 'cloud-b6' @"
{"type":"flow","title":"Fara Amfani da Cloud","items":["Bude Asusu","Saita Aminci","Loda Fayiloli","Raba Link"],"caption":"Fara amfani da cloud yana da sauƙi"},{"type":"compare","title":"Cloud mai Sauki da Rikici","leftTitle":"Mai Sauki","leftItems":["Google Drive","Dropbox","Mafi sauƙi"],"rightTitle":"Mai Rikici","rightItems":["AWS","Azure","Mafi ƙarfi"]},{"type":"hub","title":"Cloud Basics","center":"Cloud","satellites":["Account","Storage","Sharing","Security"]},{"type":"stack","title":"Getting Started","items":["Deploy","Configure","Upload","Share"]}
"@

Insert-SuppDiag $f 'cloud-i1' @"
{"type":"flow","title":"Virtualization vs Containers","items":["Zaɓen Hanya","Shirya Kayan Aiki","Gwada a Wuri","Yanke Shawa"],"caption":"Virtualization da Containers suna da bambanci"},{"type":"compare","title":"VM vs Container","leftTitle":"Virtual Machine","leftItems":["Kwaikwayon dukkan kwamfuta","Nauyi","Mafi aminci"],"rightTitle":"Container","rightItems":["App kadai + bukatunta","Sauki","Mafi sauri"]},{"type":"hub","title":"Virtualization","center":"VM/Container","satellites":["Docker","Kubernetes","VMware","Hyper-V"]},{"type":"stack","title":"Container Stack","items":["Orchestration (K8s)","Container Runtime","OS","Hardware"]}
"@

Insert-SuppDiag $f 'cloud-i2' @"
{"type":"flow","title":"Scalability","items":["Gano Buƙata","Ƙara Na'urori","Rage Na'urori","Sake Gwada"],"caption":"Scalability yana ba ka damar girma da rage"},{"type":"compare","title":"Manual vs Auto Scaling","leftTitle":"Manual","leftItems":["Kai tsaye","Lokaci mai yawa","Matsalar mutum"],"rightTitle":"Auto Scaling","rightItems":["Atomatik","Mai sauri","Mafi kyau"]},{"type":"hub","title":"Scalability","center":"Scale","satellites":["Horizontal","Vertical","Auto","Manual"]},{"type":"stack","title":"Scaling Stack","items":["Monitoring","Auto-scaling","Load Balancing","Infrastructure"]}
"@

Insert-SuppDiag $f 'cloud-i3' @"
{"type":"flow","title":"Cloud Migration","items":["Tantance App","Zaɓen Hanya","Gwada","Kaddamar da Bayanai"],"caption":"Cloud migration yana buƙatar shirye-shiri"},{"type":"compare","title":"Lift & Shift vs Refactor","leftTitle":"Lift & Shift","leftItems":["Mai sauƙi","Ba a canza app","Mafi sauri"],"rightTitle":"Refactor","rightItems":["Mafi kyau","Canza app","Mafi tsawo"]},{"type":"hub","title":"Migration","center":"Migration","satellites":["Assessment","Planning","Testing","Cutover"]},{"type":"stack","title":"Migration Stack","items":["Cutover","Testing","Planning","Assessment"]}
"@

Insert-SuppDiag $f 'cloud-i4' @"
{"type":"flow","title":"Sarrafa Kuɗi a Cloud","items":["监控コスト","Rage Waste","Saita Almara","Dubawa ta Yau da kullum"],"caption":"Sarrafa kuɗi yana buƙatar kulawa ta yau da kullum"},{"type":"compare","title":"Ba a Kula da Kyau","leftTitle":"Ba a Kula ba","leftItems":["Cloud Waste","Kuɗi masu yawa","Rashin tabbaci"],"rightTitle":"Ana Kula da Kyau","rightItems":["Cost Monitoring","Rage Waste","Taimako mai kyau"]},{"type":"hub","title":"Cost Management","center":"Kuɗi","satellites":["Monitoring","Budgeting","Optimization","Reporting"]},{"type":"stack","title":"Cost Stack","items":["Reporting","Alerts","Budgets","Monitoring"]}
"@

Insert-SuppDiag $f 'cloud-i5' @"
{"type":"flow","title":"APIs a Cloud","items":["Yi Buƙata","Haɗa da Sabis","Amsa ta JSON","Yi Amfani da Bayanai"],"caption":"APIs suna haɗa kayan aiki daban-daban"},{"type":"compare","title":"REST vs GraphQL","leftTitle":"REST","leftItems":["Girma ɗaya","Mafi sauƙi","Mafi yawan amfani"],"rightTitle":"GraphQL","rightItems":["Yayi daidai","Mafi kyau ga mobile","Mafi inganci"]},{"type":"hub","title":"Cloud APIs","center":"API","satellites":["Storage","AI","Notifications","Database"]},{"type":"stack","title":"API Stack","items":["Rate Limiting","Authentication","Routing","Response"]}
"@

Insert-SuppDiag $f 'cloud-i6' @"
{"type":"flow","title":"Multi-cloud vs Hybrid","items":["Gano Bukata","Zaɓen Hanya","Shirya Haɗi","Gwada da Yi Amfani"],"caption":"Multi-cloud yana amfani da kamfanoni fiye da ɗaya"},{"type":"compare","title":"Multi-cloud vs Hybrid","leftTitle":"Multi-cloud","leftItems":["AWS + Google Cloud","Guje wa dogaro da ɗaya","Mafi tsaro"],"rightTitle":"Hybrid","rightItems":["Cloud + Na Gida","Mafi sauƙin sarrafa","Mafi aminci"]},{"type":"hub","title":"Cloud Strategies","center":"Strategy","satellites":["Multi-cloud","Hybrid","Single Cloud","On-Premise"]},{"type":"stack","title":"Cloud Strategy","items":["Hybrid","Multi-cloud","Single Cloud","On-Premise"]}
"@

Insert-SuppDiag $f 'cloud-a1' @"
{"type":"flow","title":"Kubernetes","items":["Gina Container","Yi Deploy","Saita Scaling","Kula da Aiki"],"caption":"Kubernetes yana sarrafa containers a gauga"},{"type":"compare","title":"Kubernetes da Docker","leftTitle":"Docker","leftItems":["Gina container ɗaya","Mai sauƙi","Mafi girma"],"rightTitle":"Kubernetes","rightItems":["Sarrafa containers da yawa","Mafi rikici","Mafi ƙarfi"]},{"type":"hub","title":"Kubernetes","center":"K8s","satellites":["Pods","Services","Deployments","Nodes"]},{"type":"stack","title":"K8s Stack","items":["Application","Pods","Nodes","Cluster"]}
"@

Insert-SuppDiag $f 'cloud-a2' @"
{"type":"flow","title":"Serverless","items":["Rubuta Code","Yi Deploy","Aiki Ya Faru","Biyan Kuɗi Kawai"],"caption":"Serverless yana ba ka damar aiki ba tare da server ba"},{"type":"compare","title":"Serverless da Traditional","leftTitle":"Traditional","leftItems":["Kana sarrafa server","Kana buƙatar kulawa","Kuɗi koyaushe"],"rightTitle":"Serverless","rightItems":["Ba ka sarrafa server","Ba ka buƙatar kulawa","Biyan kuɗi kawai lokacin amfani"]},{"type":"hub","title":"Serverless","center":"FaaS","satellites":["AWS Lambda","Azure Functions","Google Cloud Functions"]},{"type":"stack","title":"Serverless Stack","items":["API Gateway","Function","Event Source","Database"]}
"@

Insert-SuppDiag $f 'cloud-a3' @"
{"type":"flow","title":"Cloud Architecture","items":["Zaɓen Patterns","Gina Microservices","Saita Load Balancing","监控 Performance"],"caption":"Cloud architecture yana buƙatar tsari mai kyau"},{"type":"compare","title":"Monolith vs Microservices","leftTitle":"Monolith","leftItems":["App ɗaya","Mai sauƙi","Mafi rikici"],"rightTitle":"Microservices","rightItems":["Ayyuka da yawa","Mai rikici","Mafi kyau"]},{"type":"hub","title":"Architecture","center":"Architecture","satellites":["Microservices","Monolith","Event-Driven","Serverless"]},{"type":"stack","title":"Architecture Stack","items":["Load Balancer","Microservices","Data Layer","Infrastructure"]}
"@

Insert-SuppDiag $f 'cloud-a4' @"
{"type":"flow","title":"DevOps CI/CD","items":["Kara Code","Atomatik Gwaji","Atomatik Gina","Saki zuwa Production"],"caption":"CI/CD yana sauraka don sauraron sauri"},{"type":"compare","title":"CI vs CD","leftTitle":"CI (Continuous Integration)","leftItems":["Haɗa code","Gwada atomatik","Gano matsala nan take"],"rightTitle":"CD (Continuous Delivery)","rightItems":["Shirya deploy","Saki zuwa production","Atomatik"]},{"type":"hub","title":"DevOps","center":"DevOps","satellites":["CI","CD","Monitoring","Infrastructure"]},{"type":"stack","title":"CI/CD Stack","items":["Production","Staging","Testing","Build"]}
"@

Insert-SuppDiag $f 'cloud-a5' @"
{"type":"flow","title":"Cloud Databases","items":["Zaɓen DB","Shirya Schema","Loda Bayanai","Haɗa da App"],"caption":"Cloud databases suna da fa'idodi da yawa"},{"type":"compare","title":"SQL vs NoSQL","leftTitle":"SQL","leftItems":["Tsari mai tsayayye","ACID","Misali: PostgreSQL"],"rightTitle":"NoSQL","rightItems":["Tsari mai sassauci","Scalability","Misali: MongoDB"]},{"type":"hub","title":"Cloud Databases","center":"DB","satellites":["PostgreSQL","MongoDB","DynamoDB","Redis"]},{"type":"stack","title":"Database Stack","items":["Caching","Application","Database","Storage"]}
"@

Insert-SuppDiag $f 'cloud-a6' @"
{"type":"flow","title":"Hanyar zuwa Sana'o'in Cloud","items":["Koyon Fasaha","Ginawa a AWS/Azure","Shirya Certification","Neman Aiki"],"caption":"Sana'o'in cloud suna buƙatar fasaha da tabbaci"},{"type":"compare","title":"Cloud Engineer vs Architect","leftTitle":"Cloud Engineer","leftItems":["Yana gina","Yana sarrafa","Mafi aikace-aikace"],"rightTitle":"Cloud Architect","rightItems":["Yana tsara","Yana yanke shawara","Mafi ƙarfi"]},{"type":"hub","title":"Cloud Careers","center":"Cloud","satellites":["Engineer","Architect","DevOps","Security"]},{"type":"stack","title":"Cloud Career","items":["Security","Architecture","Engineering","Operations"]}
"@

Write-Output "Cloud course done"

# ============================================================
# CYBERSECURITY - 18 lessons
# ============================================================
$f = "$targetDir\cybersecurity.json"

Insert-SuppDiag $f 'cyber-b1' @"
{"type":"flow","title":"Matakai na Tsaro","items":["Gano Barazana","Rufe Hadari","Gwada Tsaro","Sake Dubawa"],"caption":"Tsaron yanar gizo yana buƙatar matakan guda 4"},{"type":"compare","title":"Tsaro da Rashin Tsaro","leftTitle":"Mai Tsaro","leftItems":["Kalmomin sirri masu ƙarfi","2FA a yanki","Backup akai-akai"],"rightTitle":"Mai Rashin Tsaro","rightItems":["Kalmar sirri mai sauƙi","Babu 2FA","Babu backup"]},{"type":"hub","title":"Cybersecurity","center":"Tsaro","satellites":["Network","Application","Data","User"]},{"type":"stack","title":"Security Stack","items":["Physical","Network","Application","Data"]}
"@

Insert-SuppDiag $f 'cyber-b2' @"
{"type":"flow","title":"Gina Kalmar Sirri","items":["Zaɓi Haruffa 10+","Haɗa Lambobi","Haɗa Alamomi","Guje wa Sunanka"],"caption":"Kalmar sirri mai ƙarfi yana kare asusunka"},{"type":"compare","title":"Kalmar Sirri Mai Rauni da Mai ƙarfi","leftTitle":"Mai Rauni","leftItems":["\"password123\"","Sunanka + Ranar Haihuwa","Mafi sauƙin guess"],"rightTitle":"Mai ƙarfi","rightItems":["\"Kt9#mPz2!qL\"","10+ Haruffa, Alamomi","Mafi ƙarfin tsaro"]},{"type":"hub","title":"Password Security","center":"Kalmar Sirri","satellites":["Length","Complexity","Uniqueness","Rotation"]},{"type":"stack","title":"Password Stack","items":["Password Manager","2FA","Strong Password","Regular Rotation"]}
"@

Insert-SuppDiag $f 'cyber-b3' @"
{"type":"flow","title":"Yadda Phishing Ke","items":["Mai Laifi Ya Aika Imel","Ya Kirkiri Tsaro","Ka Danna Link","Ya Samu Bayananka"],"caption":"Phishing yana amfani da salo na mutumCi"},{"type":"compare","title":"Imel Mai Tsaro da Mara Tsaro","leftTitle":"Mai Tsaro","leftItems":["Sunan kamfani","Alamar kulle","Link mai dacewa"],"rightTitle":"Mara Tsaro","rightItems":["Sunu mara sanu","Babu alamar kulle","Link mai ban tsoro"]},{"type":"hub","title":"Phishing","center":"Phishing","satellites":["Email","SMS","Social Media","Phone"]},{"type":"stack","title":"Phishing Stack","items":["Awareness","Email Filter","Link Verification","Reporting"]}
"@

Insert-SuppDiag $f 'cyber-b4' @"
{"type":"flow","title":"Yadda Malware Ke","items":["Ka Danna Link","Malware Ya Shiga","Ya Rufe Bayananka","Ya Rarraba Ta"],"caption":"Malware yana shiga ta hanyar danna link ko sauke fayil"},{"type":"compare","title":"Virus vs Trojan vs Ransomware","leftTitle":"Virus","leftItems":["Yana warkarwa","Yana kara kanta","Yana lalata fayiloli"],"rightTitle":"Trojan","rightItems":["Yana tspersona kamar app mai kyau","Yana buɗe dama","Yana rufe bayanai"]},{"type":"hub","title":"Malware","center":"Malware","satellites":["Virus","Trojan","Ransomware","Spyware"]},{"type":"stack","title":"Malware Defense","items":["Antivirus","Firewall","Updates","Awareness"]}
"@

Insert-SuppDiag $f 'cyber-b5' @"
{"type":"flow","title":"Yadda Social Engineering","items":["Mai Laifi Ya Kira","Ya Kirkiri Tsoro","Ka Amince","Ka Bayar da Bayani"],"caption":"Social engineering yana amfani da salon mutumCi"},{"type":"compare","title":"Social Engineering da Hack","leftTitle":"Social Engineering","leftItems":["Yana amfani da mutane","Mafi sauƙi","Ba a buƙatar fasaha"],"rightTitle":"Hack","rightItems":["Yana amfani da fasaha","Mafi rikici","Buƙatar ilimi"]},{"type":"hub","title":"Social Engineering","center":"SE","satellites":["Phishing","Pretexting","Baiting","Tailgating"]},{"type":"stack","title":"SE Defense","items":["Training","Verification","Policies","Incident Response"]}
"@

Insert-SuppDiag $f 'cyber-b6' @"
{"type":"flow","title":"Tsaron Wi-Fi","items":["Canza Kalmar Asali","Kunna WPA3","Ɓoye SSID","Duba Masu Haɗi"],"caption":"Wi-Fi mai tsaro yana kare cibiyar sadarwarka"},{"type":"compare","title":"Wi-Fi na Gida da na Jama'a","leftTitle":"Wi-Fi na Gida","leftItems":["Kalmar sirri mai ƙarfi","WPA3","Mafi aminci"],"rightTitle":"Wi-Fi na Jama'a","rightItems":["Ba a buƙatar kalmar sirri","Mafi ban tsoro","Guji ayyuka masu sirri"]},{"type":"hub","title":"Wi-Fi Security","center":"Wi-Fi","satellites":["WPA3","Password","SSID","MAC Filter"]},{"type":"stack","title":"Wi-Fi Stack","items":["Encryption","Authentication","Network","Physical"]}
"@

Insert-SuppDiag $f 'cyber-i1' @"
{"type":"flow","title":"Tsaro na Waya","items":["Sanya PIN mai ƙarfi","Kunna Fingerprint","Saita Find My Device","Yi Backup"],"caption":"Wayar ka tana buƙatar tsaro da yawa"},{"type":"compare","title":"Android vs iOS Security","leftTitle":"Android","leftItems":["Mafi yawan amfani","Mafi yawan hadari","Google Play Protect"],"rightTitle":"iOS","rightItems":["Mafi aminci","Mafi ƙarfin tsaro","App Store Review"]},{"type":"hub","title":"Mobile Security","center":"Waya","satellites":["PIN","Fingerprint","Backup","Find My"]},{"type":"stack","title":"Mobile Security Stack","items":["App Security","OS Security","Network","Physical"]}
"@

Insert-SuppDiag $f 'cyber-i2' @"
{"type":"flow","title":"Sirri a Sada Zumunta","items":["Saita Privacy Settings","Guje wa Amince da Bakon","Rufe Asusu","Duba Saituni"],"caption":"Sirri yana buƙatar kulawa a kaɗan"},{"type":"compare","title":"Facebook vs WhatsApp","leftTitle":"Facebook","leftItems":["Mafi buɗɗewa","Mafi yawan bayanai","Mafi yawan hadari"],"rightTitle":"WhatsApp","rightItems":["Yana encryption","Mafi ƙarfin sirri","Mafi ƙarfin tsaro"]},{"type":"hub","title":"Privacy","center":"Sirri","satellites":["Settings","Encryption","Data Sharing","Permissions"]},{"type":"stack","title":"Privacy Stack","items":["Permissions","Encryption","Settings","Awareness"]}
"@

Insert-SuppDiag $f 'cyber-i3' @"
{"type":"flow","title":"Nau'ukan Zamba","items":["SIM Swap","Lottery Scam","Zamba ta Sayayya","Phishing"],"caption":"Zamocin yanar gizo yana da nau'o'i da yawa"},{"type":"compare","title":"Online vs Offline Scam","leftTitle":"Online","leftItems":["Phishing","Social Media","Email"],"rightTitle":"Offline","rightItems":["Phone","SMS","In Person"]},{"type":"hub","title":"Scam Types","center":"Zamba","satellites":["SIM Swap","Lottery","Phishing","Investment"]},{"type":"stack","title":"Scam Defense","items":["Awareness","Verification","Reporting","Education"]}
"@

Insert-SuppDiag $f 'cyber-i4' @"
{"type":"flow","title":"Tsaro na Kuɗi","items":["Tabbatar da HTTPS","Ƙarara CVV","Kada ka Rasa OTP","Duba Tarihin Tranzactions"],"caption":"Kuɗinku yana buƙatar tsaro na musamman"},{"type":"compare","title":"Safe vs Unsafe","leftTitle":"Mai Aminci","leftItems":["HTTPS","OTP","Kalmomin sirri masu ƙarfi"],"rightTitle":"Mara Aminci","rightItems":["HTTP","Ba a buƙatar OTP","Kalmar sirri mai sauƙi"]},{"type":"hub","title":"Financial Security","center":"Kuɗi","satellites":["HTTPS","OTP","CVV","PIN"]},{"type":"stack","title":"Payment Security","items":["Encryption","Authentication","Monitoring","Fraud Detection"]}
"@

Insert-SuppDiag $f 'cyber-i5' @"
{"type":"flow","title":"2FA","items":["Shigar da Kalmar Sirri","Sai Lambar SMS/App","An Tabbatar da Kai","Shiga Asusu"],"caption":"2FA yana kara tabbatarwa ta hanyar biyu"},{"type":"compare","title":"SMS vs App 2FA","leftTitle":"SMS","leftItems":["Mai sauƙi","Mafi ban tsoro","SIM Swap Hadari"],"rightTitle":"App","rightItems":["Mafi aminci","Ba a buƙatar hanyar sadarwa","Mafi ƙarfi"]},{"type":"hub","title":"2FA Methods","center":"2FA","satellites":["SMS","Authenticator App","Hardware Key","Biometrics"]},{"type":"stack","title":"2FA Stack","items":["Something You Know","Something You Have","Something You Are","Verification"]}
"@

Insert-SuppDiag $f 'cyber-i6' @"
{"type":"flow","title":"Yadda Zaka Tana","items":["Sabunta Software","Yi Backup","Kunna 2FA","Kar Ka Danna Link"],"caption":"Hanyoyin tsaro na yau da kullum suna da muhimmanci"},{"type":"compare","title":"Good vs Bad Habits","leftTitle":"Kyakkyawan Hanya","leftItems":["Sabuntawa akai-akai","Backup","2FA","Tuna"],"rightTitle":"Mara Kyau","rightItems":["Rashin sabuntawa","Ba a backup","Babu 2FA","Danna link ba da izini"]},{"type":"hub","title":"Security Habits","center":"Tsaro","satellites":["Update","Backup","2FA","Awareness"]},{"type":"stack","title":"Security Checklist","items":["2FA","Backup","Updates","Password Manager"]}
"@

Insert-SuppDiag $f 'cyber-a2' @"
{"type":"flow","title":"Encryption","items":["Zaɓen makulli","Encryption ta makulli","Aika bayanai","Bude da makulli"],"caption":"Encryption yana rufe bayanai domin kare su"},{"type":"compare","title":"Symmetric vs Asymmetric","leftTitle":"Symmetric","leftItems":["Makulli ɗaya","Mai sauri","Misali: AES"],"rightTitle":"Asymmetric","rightItems":["Makulli biyu","Mai aminci","Misali: RSA"]},{"type":"hub","title":"Encryption","center":"Encryption","satellites":["Symmetric","Asymmetric","Hashing","Digital Signatures"]},{"type":"stack","title":"Encryption Stack","items":["Application","Transport","Storage","Key Management"]}
"@

Insert-SuppDiag $f 'cyber-a3' @"
{"type":"flow","title":"Tsaron Cloud","items":["Gano Alhakin","Saita Tsaro","监控 Activity","Yi Response"],"caption":"Tsaron cloud yana buƙatar fahimtar shared responsibility"},{"type":"compare","title":"Shared Responsibility","leftTitle":"Kamfanin Cloud","leftItems":["Tsaron hardware","Tsaron network","Tsaron data center"],"rightTitle":"Kai (Mai Amfani)","rightItems":["Tsaron data","Tsaron app","Tsaron saiti"]},{"type":"hub","title":"Cloud Security","center":"Cloud","satellites":["IAM","Encryption","Monitoring","Compliance"]},{"type":"stack","title":"Cloud Security Stack","items":["Compliance","Monitoring","IAM","Infrastructure"]}
"@

Insert-SuppDiag $f 'cyber-a4' @"
{"type":"flow","title":"Ethical Hacking","items":["Sami Izini","Yi Nazarin Scope","Yi Gwaji","Rubuta Rahoto"],"caption":"Ethical hacking yana buƙatar izini da dokoki"},{"type":"compare","title":"White Hat vs Black Hat","leftTitle":"White Hat","leftItems":["Bisa izini","Domin inganta tsaro","Mai kyau"],"rightTitle":"Black Hat","rightItems":["Ba bisa izini","Domin cutarwa","Mai ban tsoro"]},{"type":"hub","title":"Hacking Types","center":"Hacking","satellites":["White Hat","Black Hat","Grey Hat","Bug Bounty"]},{"type":"stack","title":"Pentest Stack","items":["Report","Exploitation","Scanning","Reconnaissance"]}
"@

Insert-SuppDiag $f 'cyber-a5' @"
{"type":"flow","title":"Incident Response","items":["Ganowa","Killewa (Contain)","Kawarwa","Dawowa","Koyo"],"caption":"Incident response yana buƙatar tsari da shiri"},{"type":"compare","title":"Prepared vs Unprepared","leftTitle":"Shirye","leftItems":["Tsari yana shirye","Roles suna aiki","Mafi sauri"],"rightTitle":"Ba a Shirye ba","rightItems":["Rudani","Yiwuwar asarar","Mafi tsawo"]},{"type":"hub","title":"Incident Response","center":"IR","satellites":["Detection","Containment","Eradication","Recovery"]},{"type":"stack","title":"IR Stack","items":["Post-Incident","Recovery","Containment","Detection"]}
"@

Insert-SuppDiag $f 'cyber-a6' @"
{"type":"flow","title":"Tsaro ga Kasuwanci","items":["Horas da Ma'aikata","Saita 2FA","Yi Backup","Iyakance Damar Shiga"],"caption":"Kasuwanci karami tana buƙatar matakan tsaro masu sauƙi"},{"type":"compare","title":"Kasuwanci Mai da Ba tare da Tsaro","leftTitle":"Mai Tsaro","leftItems":["Horas da ma'aikata","2FA","Backup","Iyakance shiga"],"rightTitle":"Ba tare da Tsaro","rightItems":["Rashin horaswa","Babu 2FA","Babu backup","Kowa yana shiga"]},{"type":"hub","title":"Business Security","center":"Kasuwanci","satellites":["Training","2FA","Backup","Access Control"]},{"type":"stack","title":"Business Security Stack","items":["Compliance","Training","Technical Controls","Policies"]}
"@

Write-Output "Cybersecurity course done"

# ============================================================
# DATA SCIENCE - 18 lessons
# ============================================================
$f = "$targetDir\data_science.json"

Insert-SuppDiag $f 'ds-b1' @"
{"type":"flow","title":"Matakai na Data Science","items":["Tattara Bayanai","Shirya Bayanai","Nazarin Bayanai","Nuna Sakamako"],"caption":"Data Science yana haɗa kididdiga da shirye-shirye"},{"type":"compare","title":"Data Science da Related","leftTitle":"Data Science","leftItems":["Kididdiga","Shirye-shirye","Ilimin Kasuwanci"],"rightTitle":"Related Fields","rightItems":["Machine Learning","AI","Statistics"]},{"type":"hub","title":"Data Science","center":"DS","satellites":["Statistics","Programming","Business","ML"]},{"type":"stack","title":"DS Stack","items":["Visualization","Analysis","Machine Learning","Data"]}
"@

Insert-SuppDiag $f 'ds-b2' @"
{"type":"flow","title":"Nau'ukan Bayanai","items":["Structured","Unstructured","Semi-structured","Time Series"],"caption":"Bayanai suna da nau'o'i da yawa"},{"type":"compare","title":"Structured vs Unstructured","leftTitle":"Structured","leftItems":["Teburin Excel","Tsari mai tsayayye","Mafi sauƙin nazarin"],"rightTitle":"Unstructured","rightItems":["Rubutu, Hoto","Babu tsari mai tsayayye","Mafi rikici"]},{"type":"hub","title":"Data Types","center":"Bayanai","satellites":["Structured","Unstructured","Semi-structured","Streaming"]},{"type":"stack","title":"Data Stack","items":["Storage","Processing","Analysis","Visualization"]}
"@

Insert-SuppDiag $f 'ds-b3' @"
{"type":"flow","title":"Excel don Data","items":["Shigar da Bayanai","Yi Pivot Table","Yi Graphics","Yanke Shawara"],"caption":"Excel yana da kayan aiki masu amfani don data"},{"type":"compare","title":"Excel vs Google Sheets","leftTitle":"Excel","leftItems":["Mafi ƙarfi","Mafi yawan fasali","Mafi yawan amfani"],"rightTitle":"Google Sheets","rightItems":["Kyauta","Yana haɗawa da web","Mafi sauƙin raba"]},{"type":"hub","title":"Spreadsheets","center":"Sheets","satellites":["Excel","Google Sheets","Numbers","LibreOffice"]},{"type":"stack","title":"Spreadsheet Stack","items":["Macros","Pivot Tables","Charts","Formulas"]}
"@

Insert-SuppDiag $f 'ds-b4' @"
{"type":"flow","title":"Data Visualization","items":["Zaɓen Nau'in Hoto","Shirya Bayanai","Yi Hoto","Nuna Sakamako"],"caption":"Hotuna yana sa bayanai su zama sauƙin fahimta"},{"type":"compare","title":"Bar vs Line vs Pie","leftTitle":"Bar Chart","leftItems":["Mafi kyau ga categories","Mai sauƙin fahimta","Mafi yawan amfani"],"rightTitle":"Line Chart","rightItems":["Mafi kyau ga time series","Nuna trend","Mai sauki"]},{"type":"hub","title":"Chart Types","center":"Charts","satellites":["Bar","Line","Pie","Scatter"]},{"type":"stack","title":"Viz Stack","items":["Dashboard","Reports","Charts","Raw Data"]}
"@

Insert-SuppDiag $f 'ds-b5' @"
{"type":"flow","title":"Kididdiga","items":["Mean","Median","Mode","Standard Deviation"],"caption":"Kididdiga tana taimaka wajen fahimtar bayanai"},{"type":"compare","title":"Mean vs Median","leftTitle":"Mean","leftItems":["Adadin duka","Yana da tasiri daga outliers","Mafi sauƙin la'akari"],"rightTitle":"Median","rightItems":["Tsakiyar bayanai","Ba a shafi da outliers","Mafi aminci"]},{"type":"hub","title":"Statistics","center":"Stats","satellites":["Mean","Median","Mode","Std Dev"]},{"type":"stack","title":"Stats Stack","items":["Inferential","Descriptive","Probability","Data"]}
"@

Insert-SuppDiag $f 'ds-b6' @"
{"type":"flow","title":"Python da Pandas","items":["Shigar da Python","Koyi Pandas","Shigar da Bayanai","Yi Nazarin"],"caption":"Python da Pandas suna da amfani sosai don data"},{"type":"compare","title":"Python vs R","leftTitle":"Python","leftItems":["Mafi yawan amfani","Mafi yawan libraries","Mafi sauƙin koyo"],"rightTitle":"R","rightItems":["Mafi kyau ga statistics","Mafi kyau ga visualization","Mafi kyau ga research"]},{"type":"hub","title":"Data Tools","center":"Tools","satellites":["Python","R","SQL","Excel"]},{"type":"stack","title":"Python DS Stack","items":["Visualization (Matplotlib)","Analysis (Pandas)","ML (Scikit-learn)","Data (NumPy)"]}
"@

Insert-SuppDiag $f 'ds-i1' @"
{"type":"flow","title":"Data Cleaning","items":["Gano Matsaloli","Rufe Matsaloli","Tabbatar da Bayanai","Yi Validation"],"caption":"Data cleaning yana da muhimmanci don ingancin nazarin"},{"type":"compare","title":"Kafin da Bayan Tsarkakewa","leftTitle":"Kafin","leftItems":["Bayanai suka bata","Kwafi","Mara tsari"],"rightTitle":"Bayan","rightItems":["Cikakke da Daidai","Shiryayye","Mafi kyau"]},{"type":"hub","title":"Data Quality","center":"Quality","satellites":["Completeness","Accuracy","Consistency","Timeliness"]},{"type":"stack","title":"Cleaning Stack","items":["Validation","Standardization","Deduplication","Imputation"]}
"@

Insert-SuppDiag $f 'ds-i2' @"
{"type":"flow","title":"SQL","items":["Shigar da Query","Yi SELECT","Yi WHERE","Nuna Sakamako"],"caption":"SQL yana amfani don tattara bayanai daga database"},{"type":"compare","title":"SQL vs NoSQL","leftTitle":"SQL","leftItems":["Teburin","Tsari mai tsayayye","ACID"],"rightTitle":"NoSQL","rightItems":["Document/KV","Tsari mai sassauci","Scalability"]},{"type":"hub","title":"SQL","center":"SQL","satellites":["SELECT","JOIN","WHERE","GROUP BY"]},{"type":"stack","title":"SQL Stack","items":["Application","Query Engine","Storage","Index"]}
"@

Insert-SuppDiag $f 'ds-i3' @"
{"type":"flow","title":"ML don Data Science","items":["Training Data","Horas da Model","Test Data","Hasashe"],"caption":"ML yana amfani da data don hasashen makomar"},{"type":"compare","title":"Training vs Test","leftTitle":"Training","leftItems":["Bayanai don koyarwa","80% na data","Model yana koyo"],"rightTitle":"Test","rightItems":["Bayanai don gwaji","20% na data","Tabbatar da inganci"]},{"type":"hub","title":"ML Pipeline","center":"ML","satellites":["Data Prep","Training","Evaluation","Deployment"]},{"type":"stack","title":"ML Stack","items":["Deployment","Model","Features","Data"]}
"@

Insert-SuppDiag $f 'ds-i4' @"
{"type":"flow","title":"A/B Testing","items":["Zaɓin Zabin A","Zaɓin Zabin B","Yi Gwaji","Yanke Shawara"],"caption":"A/B testing yana taimaka wajen yanke shawara da bayanai"},{"type":"compare","title":"A vs B","leftTitle":"Zabi A","leftItems":["62% conversion","Mafi sauƙi","Mafi araha"],"rightTitle":"Zabi B","rightItems":["85% conversion","Mafi rikici","Mafi tsada"]},{"type":"hub","title":"A/B Testing","center":"A/B","satellites":["Control","Variant","Metrics","Significance"]},{"type":"stack","title":"A/B Stack","items":["Analysis","Testing","Implementation","Hypothesis"]}
"@

Insert-SuppDiag $f 'ds-i5' @"
{"type":"flow","title":"Dashboards","items":["Zaɓin Kayan Aiki","Haɗa Bayanai","Gina Dashboard","Raba da Jama'a"],"caption":"Dashboards yana nuna bayanai a tsaye"},{"type":"compare","title":"Tableau vs Power BI","leftTitle":"Tableau","leftItems":["Mafi kyau ga visualization","Mafi ƙarfi","Mafi tsada"],"rightTitle":"Power BI","rightItems":["Mai araha","Yana haɗawa da Excel","Mafi sauƙi"]},{"type":"hub","title":"BI Tools","center":"Dashboard","satellites":["Tableau","Power BI","Looker","Metabase"]},{"type":"stack","title":"BI Stack","items":["Reports","Dashboards","Data Warehouse","ETL"]}
"@

Insert-SuppDiag $f 'ds-i6' @"
{"type":"flow","title":"Data Ethics","items":["Sami Izini","Rufe Sirri","Guje wa Bias","Yi Transparency"],"caption":"Data ethics yana da muhimmanci wajen kare mutane"},{"type":"compare","title":"Ethical vs Unethical","leftTitle":"Ethical","leftItems":["Izini daga masu amfani","Rufe sirri","Mai gaskiya"],"rightTitle":"Unethical","rightItems":["Ba tare da izini ba","Rufe bayanai","Mai ɓatanci"]},{"type":"hub","title":"Data Ethics","center":"Ethics","satellites":["Privacy","Consent","Bias","Transparency"]},{"type":"stack","title":"Ethics Stack","items":["Compliance","Governance","Auditing","Policies"]}
"@

Insert-SuppDiag $f 'ds-a1' @"
{"type":"flow","title":"Big Data","items":["Tattara Bayanai","Sarrafa Bayanai","Adana Bayanai","Nazarin Bayanai"],"caption":"Big Data yana buƙatar kayan aiki na musamman"},{"type":"compare","title":"Traditional vs Big Data","leftTitle":"Traditional","leftItems":["Teburin Excel","Ƙanƙanin data","Mafi sauƙi"],"rightTitle":"Big Data","rightItems":["Hadoop/Spark","Manyan bayanai","Mafi rikici"]},{"type":"hub","title":"Big Data","center":"Big Data","satellites":["Volume","Velocity","Variety","Veracity"]},{"type":"stack","title":"Big Data Stack","items":["Analytics","Processing","Storage","Ingestion"]}
"@

Insert-SuppDiag $f 'ds-a2' @"
{"type":"flow","title":"Predictive Modeling","items":["Tattara Bayanai","Zaɓi Features","Horas da Model","Hasashen Makoma"],"caption":"Predictive modeling yana amfani da data don hasashen"},{"type":"compare","title":"Regression vs Classification","leftTitle":"Regression","leftItems":["Hasashen lambobi","Mafi sauƙi","Misali: Kimanin farashi"],"rightTitle":"Classification","rightItems":["Rarraba zuwa ƙungiya","Mafi rikici","Misali: Spam ba spam"]},{"type":"hub","title":"Predictive","center":"Hasashi","satellites":["Regression","Classification","Clustering","Time Series"]},{"type":"stack","title":"Model Stack","items":["Deployment","Training","Features","Data"]}
"@

Insert-SuppDiag $f 'ds-a3' @"
{"type":"flow","title":"Deep Learning","items":["Shirya Bayanai","Gina Neural Network","Horas da Network","Gwada Performance"],"caption":"Deep Learning yana amfani da neural networks mai zurfi"},{"type":"compare","title":"Traditional ML vs Deep Learning","leftTitle":"Traditional ML","leftItems":["Manual features","Ƙanƙanin data","Mafi sauƙi"],"rightTitle":"Deep Learning","rightItems":["Automatic features","Manyan bayanai","Mafi ƙarfi"]},{"type":"hub","title":"Deep Learning","center":"DL","satellites":["CNN","RNN","Transformer","GAN"]},{"type":"stack","title":"DL Stack","items":["Application","Model","Training","Data"]}
"@

Insert-SuppDiag $f 'ds-a4' @"
{"type":"flow","title":"Data Engineering","items":["Tattara Bayanai","Tsaftace su","Adana a Database","Shirya don Nazari"],"caption":"Data engineering yana shirya bayanai don nazarin"},{"type":"compare","title":"Data Engineering vs Data Science","leftTitle":"Data Engineering","leftItems":["Yana shirya bayanai","Infrastructure","ETL pipelines"],"rightTitle":"Data Science","rightItems":["Yana nazarin bayanai","Analysis","ML models"]},{"type":"hub","title":"Data Engineering","center":"DE","satellites":["Pipelines","Warehousing","ETL","Quality"]},{"type":"stack","title":"DE Stack","items":["Orchestration","Processing","Storage","Ingestion"]}
"@

Insert-SuppDiag $f 'ds-a5' @"
{"type":"flow","title":"Business Analytics","items":["Gano Tambaya","Tattara Bayanai","Nazarin Bayanai","Bayar da Shawara"],"caption":"Business analytics yana taimaka wa kamfanoni yanke shawara"},{"type":"compare","title":"Analytics vs BI","leftTitle":"Analytics","leftItems":["Nazarin zurfin","Hasashen","Mafi rikici"],"rightTitle":"BI","rightItems":["Nuna bayanai","Dashboard","Mafi sauƙi"]},{"type":"hub","title":"Business Analytics","center":"BA","satellites":["Descriptive","Diagnostic","Predictive","Prescriptive"]},{"type":"stack","title":"BA Stack","items":["Strategy","Analysis","Visualization","Data"]}
"@

Insert-SuppDiag $f 'ds-a6' @"
{"type":"flow","title":"Sana'o'in Data Science","items":["Zaɓin Sana'a","Koyon Fasaha","Gina Portfolio","Neman Aiki"],"caption":"Sana'o'in data science suna da buƙata masu yawa"},{"type":"compare","title":"Analyst vs Scientist vs Engineer","leftTitle":"Data Analyst","leftItems":["Nazarin bayanai","Dashboard","Raports"],"rightTitle":"Data Scientist","rightItems":["ML models","Prediction","Research"]},{"type":"hub","title":"DS Careers","center":"DS","satellites":["Analyst","Scientist","Engineer","ML Engineer"]},{"type":"stack","title":"DS Career","items":["ML Engineering","Data Science","Data Engineering","Analytics"]}
"@

Write-Output "Data Science course done"

# ============================================================
# MOBILE DEVELOPMENT - 18 lessons
# ============================================================
$f = "$targetDir\mobile_development.json"

Insert-SuppDiag $f 'mob-b1' @"
{"type":"flow","title":"Gina App","items":["Zaɓin Yare","Shirya UI","Haɗa Backend","Gwada da Buga"],"caption":"Gina app yana buƙatar matakan da yawa"},{"type":"compare","title":"Android vs iOS","leftTitle":"Android","leftItems":["Google ta kirkira","Kotlin/Java","Mafi yawan amfani"],"rightTitle":"iOS","rightItems":["Apple ta kirkira","Swift","Mafi aminci"]},{"type":"hub","title":"Mobile Dev","center":"Mobile","satellites":["Android","iOS","Cross-platform","Backend"]},{"type":"stack","title":"Mobile Stack","items":["Backend","Native Code","UI Framework","OS"]}
"@

Insert-SuppDiag $f 'mob-b2' @"
{"type":"flow","title":"Gina App na Farko","items":["Kirkiri Project","Rubuta Hello World","Kara Maballi","Gwada a Emulator"],"caption":"App na farko yana da sauƙi a gina"},{"type":"compare","title":"Android Studio vs Xcode","leftTitle":"Android Studio","leftItems":["Na Google","Mai kyau ga Android","Yana aiki a dukkan OS"],"rightTitle":"Xcode","rightItems":["Na Apple","Mai kyau ga iOS","Mac kadai"]},{"type":"hub","title":"Dev Tools","center":"Tools","satellites":["Android Studio","Xcode","VS Code","IntelliJ"]},{"type":"stack","title":"Dev Stack","items":["Testing","IDE","Build Tools","Language"]}
"@

Insert-SuppDiag $f 'mob-b3' @"
{"type":"flow","title":"Native vs Cross-Platform","items":["Gano Manufar","Yanayin App","Zaɓin Hanya","Yi Amfani"],"caption":"Zaɓen hanya yana dogara da manufar app ɗinka"},{"type":"compare","title":"Native vs Cross-Platform","leftTitle":"Native","leftItems":["Kotlin/Swift","Performance mai kyau","Mafi ƙarfi"],"rightTitle":"Cross-Platform","rightItems":["Dart/JavaScript","Code ɗaya","Mafi sauƙi"]},{"type":"hub","title":"Mobile Approaches","center":"Approach","satellites":["Native","Cross-platform","Hybrid","PWA"]},{"type":"stack","title":"Cross-Platform Stack","items":["Native Modules","Framework","Business Logic","Shared Code"]}
"@

Insert-SuppDiag $f 'mob-b4' @"
{"type":"flow","title":"UI/UX Design","items":["Gano Masu Amfani","Gina Wireframe","Gina Prototype","Gwada da Inganta"],"caption":"UI/UX yana da muhimmanci don gogewar mai amfani"},{"type":"compare","title":"UI vs UX","leftTitle":"UI","leftItems":["Launi","Maballi","Fuska"],"rightTitle":"UX","rightItems":["Gogewa","Sauki","Gamsuwa"]},{"type":"hub","title":"Design","center":"UI/UX","satellites":["Research","Wireframe","Prototype","Testing"]},{"type":"stack","title":"Design Stack","items":["Implementation","Prototype","Wireframe","Research"]}
"@

Insert-SuppDiag $f 'mob-b5' @"
{"type":"flow","title":"Debugging","items":["Gano Matsalar","Yi Breakpoints","Duba Logs","Gyara Code"],"caption":"Debugging yana taimaka wajen gano matsalolin"},{"type":"compare","title":"Emulator vs Real Device","leftTitle":"Emulator","leftItems":["Sauri","Ba a buƙatar waya","Ƙanƙanin resources"],"rightTitle":"Real Device","rightItems":["Tabbaci","Gwada baturi","Mai gaske"]},{"type":"hub","title":"Debugging","center":"Debug","satellites":["Breakpoints","Logs","Profiler","Crash Reports"]},{"type":"stack","title":"Debug Stack","items":["Monitoring","Logging","Profiling","Breakpoints"]}
"@

Insert-SuppDiag $f 'mob-b6' @"
{"type":"flow","title":"App na Farko","items":["Shigar Android Studio","Kirkiri Project","Rubuta Code","Gwada a Emulator"],"caption":"App na farko yana taimaka wajen koyon fasahar"},{"type":"compare","title":"Hello World vs Real App","leftTitle":"Hello World","leftItems":["App mai sauƙi","Gogewa","Ƙanƙanin aiki"],"rightTitle":"Real App","rightItems":["App mai rikici","Buƙata","Mafi yawan aiki"]},{"type":"hub","title":"First App","center":"App","satellites":["UI","Logic","Data","Testing"]},{"type":"stack","title":"First App Stack","items":["Testing","Build","Code","Setup"]}
"@

Insert-SuppDiag $f 'mob-i1' @"
{"type":"flow","title":"Flutter da React Native","items":["Zaɓin Framework","Koyi Yaren","Gina UI","Gwada a Wayar"],"caption":"Frameworks biyu suna da amfani sosai"},{"type":"compare","title":"Flutter vs React Native","leftTitle":"Flutter","leftItems":["Dart","Custom Rendering","Mafi sauri"],"rightTitle":"React Native","rightItems":["JavaScript","Native Components","Mafi yawan amfani"]},{"type":"hub","title":"Cross-Platform","center":"Framework","satellites":["Flutter","React Native","Xamarin","Ionic"]},{"type":"stack","title":"Framework Stack","items":["Native Modules","Framework","JavaScript/Dart","Platform"]}
"@

Insert-SuppDiag $f 'mob-i2' @"
{"type":"flow","title":"Local Storage","items":["Zaɓin Hanya","Saitan Storage","Adana Bayanai","Duba Bayanai"],"caption":"Local storage yana adana bayanai a wayarka"},{"type":"compare","title":"SharedPreferences vs SQLite","leftTitle":"SharedPreferences","leftItems":["Karamin bayani","Key-Value","Mafi sauƙi"],"rightTitle":"SQLite","rightItems":["Manyan bayanai","Relational","Mafi ƙarfi"]},{"type":"hub","title":"Local Storage","center":"Storage","satellites":["SharedPreferences","SQLite","Room","DataStore"]},{"type":"stack","title":"Storage Stack","items":["Cache","Database","File System","Key-Value"]}
"@

Insert-SuppDiag $f 'mob-i3' @"
{"type":"flow","title":"APIs a Mobile","items":["Yi Buƙata","Tura zuwa Server","Karba Amsa","Nuna ga Mai Amfani"],"caption":"APIs yana haɗa app da backend"},{"type":"compare","title":"REST vs GraphQL","leftTitle":"REST","leftItems":["Girma ɗaya","Mafi sauƙi","Mafi yawan amfani"],"rightTitle":"GraphQL","rightItems":["Yayi daidai","Mafi kyau ga mobile","Mafi inganci"]},{"type":"hub","title":"APIs","center":"API","satellites":["REST","GraphQL","gRPC","WebSocket"]},{"type":"stack","title":"API Stack","items":["Response","Request","Server","Client"]}
"@

Insert-SuppDiag $f 'mob-i4' @"
{"type":"flow","title":"Testing","items":["Rubuta Tests","Gwada a Emulator","Gwada a Wayar","Yi Fix"],"caption":"Testing yana tabbatar da aikin app"},{"type":"compare","title":"Unit vs Integration vs E2E","leftTitle":"Unit Test","leftItems":["Aiki ɗaya","Mai sauri","Mafi sauƙi"],"rightTitle":"Integration","rightItems":["Haɗa ayyuka","Mafi rikici","Mafi zurfin gwaji"]},{"type":"hub","title":"Testing","center":"Test","satellites":["Unit","Integration","E2E","UI"]},{"type":"stack","title":"Test Stack","items":["E2E","Integration","Unit","Linting"]}
"@

Insert-SuppDiag $f 'mob-i5' @"
{"type":"flow","title":"Buga App","items":["Kirkiri Asusu","Shirya Bayani","Gwada","Buga a Store"],"caption":"Buga app yana buƙatar shirye-shiri"},{"type":"compare","title":"Play Store vs App Store","leftTitle":"Play Store","leftItems":["Android","Mafi sauƙin buga","Mafi yawan审查"],"rightTitle":"App Store","rightItems":["iOS","Mafi ƙarfin审查","Mafi ƙarfin tsaro"]},{"type":"hub","title":"App Stores","center":"Store","satellites":["Play Store","App Store","Huawei","Samsung"]},{"type":"stack","title":"Publishing Stack","items":["Review","Submission","Preparation","Development"]}
"@

Insert-SuppDiag $f 'mob-i6' @"
{"type":"flow","title":"Monetization","items":["Zaɓin Hanya","Shirya Talla","Haɗa Payment","Dubawa"],"caption":"Monetization yana ba ka damar samun kuɗi daga app"},{"type":"compare","title":"Ads vs In-App Purchase","leftTitle":"Ads","leftItems":["Kyauta ga mai amfani","Ƙanƙanin kuɗi","Mai ban sha'awa"],"rightTitle":"In-App Purchase","rightItems":["Buƙatar biya","Manyan kuɗi","Mafi kyau"]},{"type":"hub","title":"Monetization","center":"Kuɗi","satellites":["Ads","Subscriptions","In-App Purchase","Premium"]},{"type":"stack","title":"Revenue Stack","items":["Analytics","Payment","Pricing","Monetization"]}
"@

Insert-SuppDiag $f 'mob-a1' @"
{"type":"flow","title":"Push Notifications","items":["Shirya Sako","Tura zuwa FCM/APNs","Karba a Wayar","Nuna ga Mai Amfani"],"caption":"Push notifications yana taimaka wajen tarar da masu amfani"},{"type":"compare","title":"FCM vs APNs","leftTitle":"FCM","leftItems":["Na Google","Android + iOS","Mai sauƙi"],"rightTitle":"APNs","rightItems":["Na Apple","iOS kadai","Mafi aminci"]},{"type":"hub","title":"Push","center":"Notification","satellites":["FCM","APNs","Firebase","OneSignal"]},{"type":"stack","title":"Push Stack","items":["Delivery","Service","Server","Client"]}
"@

Insert-SuppDiag $f 'mob-a2' @"
{"type":"flow","title":"Tsaron Mobile","items":["Amfani da HTTPS","Ɓoye API Keys","Saita Cert Pinning","Duba Permissions"],"caption":"Tsaron mobile app yana da muhimmanci"},{"type":"compare","title":"Safe vs Unsafe","leftTitle":"Mai Aminci","leftItems":["HTTPS","Certificate Pinning","Encryption"],"rightTitle":"Mara Aminci","rightItems":["HTTP","Ba a rufe bayanai"," plaintext"]},{"type":"hub","title":"Mobile Security","center":"Security","satellites":["HTTPS","Encryption","Authentication","Authorization"]},{"type":"stack","title":"Security Stack","items":["Runtime","Network","Storage","Code"]}
"@

Insert-SuppDiag $f 'mob-a3' @"
{"type":"flow","title":"Performance","items":["Gano Matsalar","Profiloda","Gyara Code","Sake Gwada"],"caption":"Performance optimization yana sa app ɗinka ya gudu da sauri"},{"type":"compare","title":"Before vs After Optimization","leftTitle":"Kafin","leftItems":["Sakan 5","Baturi mai yawa","Mai hankali"],"rightTitle":"Bayan","rightItems":["Sakan 1.5","Baturi ƙanƙani","Mai sauri"]},{"type":"hub","title":"Performance","center":"Performance","satellites":["Memory","CPU","Battery","Network"]},{"type":"stack","title":"Perf Stack","items":["Monitoring","Profiling","Optimization","Testing"]}
"@

Insert-SuppDiag $f 'mob-a4' @"
{"type":"flow","title":"CI/CD Mobile","items":["Kara Code","Atomatik Gwaji","Atomatik Gina","Saki zuwa Store"],"caption":"CI/CD yana sauraka don sauraron buga app"},{"type":"compare","title":"Manual vs CI/CD","leftTitle":"Manual","leftItems":["Lokaci mai yawa","Kuskure mai yawa","Mai ban sha'awa"],"rightTitle":"CI/CD","rightItems":["Atomatik","Mai sauri","Mai aminci"]},{"type":"hub","title":"CI/CD","center":"CI/CD","satellites":["Fastlane","Bitrise","GitHub Actions","Jenkins"]},{"type":"stack","title":"CI/CD Stack","items":["Store","Build","Test","Code"]}
"@

Insert-SuppDiag $f 'mob-a5' @"
{"type":"flow","title":"Wearables/IoT","items":["Zaɓin Na'ura","Haɗa ta BLE","Shirya App","Gwada a Na'ura"],"caption":"Wearables yana buƙatar fasaha na musamman"},{"type":"compare","title":"Smartwatch vs Fitness Band","leftTitle":"Smartwatch","leftItems":["Mafi ƙarfi","Mafi yawan fasali","Mafi tsada"],"rightTitle":"Fitness Band","rightItems":["Mafi sauƙi","Baturi mai tsawo","Mafi araha"]},{"type":"hub","title":"Wearables","center":"Wearable","satellites":["Smartwatch","Fitness Band","Smart Ring","Glasses"]},{"type":"stack","title":"Wearable Stack","items":["Cloud","Companion App","BLE","Wearable OS"]}
"@

Insert-SuppDiag $f 'mob-a6' @"
{"type":"flow","title":"Sana'o'in Mobile","items":["Zaɓin Sana'a","Koyon Fasaha","Gina Portfolio","Neman Aiki"],"caption":"Sana'o'in mobile development suna da buƙata masu yawa"},{"type":"compare","title":"Android vs iOS Developer","leftTitle":"Android Dev","leftItems":["Kotlin/Java","Google Play","Mafi yawan amfani"],"rightTitle":"iOS Dev","rightItems":["Swift","App Store","Mafi yawan kuɗi"]},{"type":"hub","title":"Mobile Careers","center":"Mobile","satellites":["Android Dev","iOS Dev","Cross-Platform","Lead"]},{"type":"stack","title":"Career Stack","items":["Architecture","Lead","Senior","Junior"]}
"@

Write-Output "Mobile course done"

# ============================================================
# NETWORKING - 18 lessons
# ============================================================
$f = "$targetDir\networking.json"

Insert-SuppDiag $f 'net-b1' @"
{"type":"flow","title":"Cibiyar Sadarwa","items":["Haɗa Na'urori","Saita Router","Haɗa Wi-Fi","Yi Amfani"],"caption":"Cibiyar sadarwa tana haɗa na'urori da yawa"},{"type":"compare","title":"LAN vs WAN","leftTitle":"LAN","leftItems":["Yanki ɗaya","Mai sauri","Mafi aminci"],"rightTitle":"WAN","rightItems":["Yanki mai yawa","Mai hankali","Mafi ban tsoro"]},{"type":"hub","title":"Network","center":"Network","satellites":["Router","Switch","Modem","Access Point"]},{"type":"stack","title":"Network Stack","items":["Application","Transport","Network","Physical"]}
"@

Insert-SuppDiag $f 'net-b2' @"
{"type":"flow","title":"IP Address","items":["Rarraba IP","Haɗa Na'ura","Tabbatar da Haɗi","Yi Amfani"],"caption":"IP address shaidar da ke nuna inda na'ura take"},{"type":"compare","title":"IPv4 vs IPv6","leftTitle":"IPv4","leftItems":["Lambobi 4","Mafi yawan amfani","Mafi zurfin daidaito"],"rightTitle":"IPv6","rightItems":["Lambobi mai yawa","Sabon fasali","Mafi ƙarfi"]},{"type":"hub","title":"IP Address","center":"IP","satellites":["IPv4","IPv6","Static","Dynamic"]},{"type":"stack","title":"IP Stack","items":["Subnet","Router","Gateway","Host"]}
"@

Insert-SuppDiag $f 'net-b3' @"
{"type":"flow","title":"Router vs Switch","items":["Zaɓin Na'ura","Shirya Cabling","Saitan Configuration","Yi Amfani"],"caption":"Router da Switch suna da ayyuka daban-daban"},{"type":"compare","title":"Router vs Switch vs Modem","leftTitle":"Router","leftItems":["Rarraba Wi-Fi","Haɗa network","Mafi rikici"],"rightTitle":"Switch","rightItems":["Haɗa na'urori","Network ɗaya","Mafi sauƙi"]},{"type":"hub","title":"Network Devices","center":"Na'ura","satellites":["Router","Switch","Modem","Access Point"]},{"type":"stack","title":"Device Stack","items":["Access Point","Switch","Router","Modem"]}
"@

Insert-SuppDiag $f 'net-b4' @"
{"type":"flow","title":"Wi-Fi vs Ethernet","items":["Zaɓin Hanya","Shirya Cabling","Saita Connection","Gwada Sauri"],"caption":"Ethernet yana da sauri da tabbaci fiye da Wi-Fi"},{"type":"compare","title":"Wi-Fi vs Ethernet","leftTitle":"Wi-Fi","leftItems":["Wireless","Mai sauƙi","Mai hankali"],"rightTitle":"Ethernet","rightItems":["Wired","Mai sauri","Mai aminci"]},{"type":"hub","title":"Connectivity","center":"Haɗi","satellites":["Wi-Fi","Ethernet","Fiber","Cellular"]},{"type":"stack","title":"Connectivity Stack","items":["Protocol","Physical","Link","Application"]}
"@

Insert-SuppDiag $f 'net-b5' @"
{"type":"flow","title":"Yadda Intanet Ke Aiki","items":["Ka Bude Shafi","Bukata Ta Bi Kebul","Ta Isa Server","Server Ta Amsa"],"caption":"Intanet yana aiki ta hanyar sauyon bayanai"},{"type":"compare","title":"Browser vs Server","leftTitle":"Browser","leftItems":["Yana karɓi HTML","Yana nuna shafi","Client side"],"rightTitle":"Server","rightItems":["Yana aika bayanai","Yana adana shafi","Server side"]},{"type":"hub","title":"Internet","center":"Intanet","satellites":["Browser","Server","DNS","Router"]},{"type":"stack","title":"Internet Stack","items":["Application","Transport","Internet","Link"]}
"@

Insert-SuppDiag $f 'net-b6' @"
{"type":"flow","title":"Matsalolin Network","items":["Gano Matsalar","Duba Cabling","Sake Kunna Router","Tuntuɓi ISP"],"caption":"Matsalolin network galibi suna da magani mai sauƙi"},{"type":"compare","title":"Router da Modem","leftTitle":"Router","leftItems":["Rarraba Wi-Fi","Haɗa na'urori","Mafi yawan matsala"],"rightTitle":"Modem","rightItems":["Haɗa zuwa ISP","Ƙanƙanin matsala","Mai aminci"]},{"type":"hub","title":"Troubleshoot","center":"Matsala","satellites":["Router","Modem","Cable","ISP"]},{"type":"stack","title":"Fix Stack","items":["ISP","Modem","Router","Device"]}
"@

Insert-SuppDiag $f 'net-i1' @"
{"type":"flow","title":"OSI Model","items":["Physical Layer","Data Link","Network","Transport","Session","Presentation","Application"],"caption":"OSI model yana rarraba ayyukan network zuwa katanga 7"},{"type":"compare","title":"Lower vs Upper Layers","leftTitle":"Lower Layers","leftItems":["Physical","Data Link","Network","Mafi aminci"],"rightTitle":"Upper Layers","rightItems":["Application","Presentation","Session","Mafi sauƙi"]},{"type":"hub","title":"OSI","center":"OSI","satellites":["Physical","Network","Transport","Application"]},{"type":"stack","title":"OSI Stack","items":["7. Application","4-6. Middle","2-3. Lower","1. Physical"]}
"@

Insert-SuppDiag $f 'net-i2' @"
{"type":"flow","title":"TCP/IP","items":["Yi Connection","Aika Bayanai","Tabbatar da Isarwa","Rufe Connection"],"caption":"TCP/IP yana tabbatar da isar da bayanai cikin aminci"},{"type":"compare","title":"TCP vs UDP","leftTitle":"TCP","leftItems":["Tabbatar da isarwa","Mai aminci","Mai hankali"],"rightTitle":"UDP","rightItems":["Ba ya tabbatarwa","Mai sauri","Mai ban tsoro"]},{"type":"hub","title":"Protocols","center":"Protocol","satellites":["TCP","UDP","ICMP","ARP"]},{"type":"stack","title":"TCP/IP Stack","items":["Application","Transport","Internet","Link"]}
"@

Insert-SuppDiag $f 'net-i3' @"
{"type":"flow","title":"Yadda DNS Ke Aiki","items":["Ka Rubuta URL","DNS Server Ya Tambaya","Ya Bada IP","Browser Ya Hade"],"caption":"DNS yana canza sunan shafi zuwa IP address"},{"type":"compare","title":"DNS vs IP","leftTitle":"DNS","leftItems":["Sunan mutum","Mai sauƙin tunani","google.com"],"rightTitle":"IP","rightItems":["Lambobi","Mafi aminci","142.250.80.46"]},{"type":"hub","title":"DNS","center":"DNS","satellites":["Recursive","Root","TLD","Authoritative"]},{"type":"stack","title":"DNS Stack","items":["Cache","Recursive","Root","TLD"]}
"@

Insert-SuppDiag $f 'net-i4' @"
{"type":"flow","title":"Bandwidth vs Latency","items":["Gano Matsalar","Duba Nisa","Duba Cabling","Yan Abin da ke shafa"],"caption":"Bandwidth shi ne fadin hanyar, latency shi ne jinkiri"},{"type":"compare","title":"Bandwidth vs Latency","leftTitle":"Bandwidth","leftItems":["Fadin Hanya","Adadin bayanai","Mbps"],"rightTitle":"Latency","rightItems":["Tsawon Hanya","Jinkirin isarwa","ms"]},{"type":"hub","title":"Performance","center":"Performance","satellites":["Bandwidth","Latency","Jitter","Packet Loss"]},{"type":"stack","title":"Performance Stack","items":["ISP","Router","Cable","Device"]}
"@

Insert-SuppDiag $f 'net-i5' @"
{"type":"flow","title":"VPN","items":["Zaɓi VPN Provider","Shigar da App","Haɗa da Server","Yi Amfani"],"caption":"VPN yana rufe bayananku ta hanyar encryption"},{"type":"compare","title":"Free vs Paid VPN","leftTitle":"Free VPN","leftItems":["Kyauta","Ƙanƙanin aminci","Mai hankali"],"rightTitle":"Paid VPN","rightItems":["Biya","Mafi aminci","Mai sauri"]},{"type":"hub","title":"VPN","center":"VPN","satellites":["Encryption","Tunnel","Server","Protocol"]},{"type":"stack","title":"VPN Stack","items":["Application","Encryption","Tunnel","Internet"]}
"@

Insert-SuppDiag $f 'net-i6' @"
{"type":"flow","title":"Tsaron Network","items":["Saita WPA3","Canza Kalmar Asali","Kunna Firewall","Duba Haɗi"],"caption":"Tsaron network yana kare cibiyar sadarwarka"},{"type":"compare","title":"WPA2 vs WPA3","leftTitle":"WPA2","leftItems":["Mafi yawan amfani","Mafi sauƙin keta","Mafi araha"],"rightTitle":"WPA3","rightItems":["Sabon fasali","Mafi ƙarfin tsaro","Mafi aminci"]},{"type":"hub","title":"Network Security","center":"Security","satellites":["WPA3","Firewall","VPN","IDS"]},{"type":"stack","title":"Security Stack","items":["Monitoring","Firewall","Encryption","Access Control"]}
"@

Insert-SuppDiag $f 'net-a2' @"
{"type":"flow","title":"Load Balancing","items":["Yi Buƙata","Rarraba zuwa Servers","Dubawa","Sake Rarraba"],"caption":"Load balancing yana rarraba bukatun zuwa servers da yawa"},{"type":"compare","title":"Single vs Multiple Servers","leftTitle":"Server ɗaya","leftItems":["Mai sauƙi","瓶颈","Mafi ban tsoro"],"rightTitle":"Servers da yawa","rightItems":["Mai rikici","Mai ƙarfi","Mai aminci"]},{"type":"hub","title":"Load Balancing","center":"LB","satellites":["Round Robin","Least Connections","IP Hash","Weighted"]},{"type":"stack","title":"LB Stack","items":["Health Checks","Algorithm","Backend","Frontend"]}
"@

Insert-SuppDiag $f 'net-a3' @"
{"type":"flow","title":"HTTP/3","items":["Karban Buƙata","QUIC Protocol","Haɗa da Server","Aika Bayanai"],"caption":"HTTP/3 yana amfani da QUIC don sauri da aminci"},{"type":"compare","title":"HTTP/2 vs HTTP/3","leftTitle":"HTTP/2","leftItems":["TCP","Mai aminci","Mafi yawan amfani"],"rightTitle":"HTTP/3","rightItems":["QUIC","Mafi sauri","Sabon fasali"]},{"type":"hub","title":"HTTP","center":"HTTP","satellites":["HTTP/1.1","HTTP/2","HTTP/3","QUIC"]},{"type":"stack","title":"HTTP Stack","items":["Application","Transport","QUIC","UDP"]}
"@

Insert-SuppDiag $f 'net-a4' @"
{"type":"flow","title":"SDN","items":["Control Plane","Data Plane","Management Plane","Application Plane"],"caption":"SDN yana rarraba ayyukan network zuwa katanga"},{"type":"compare","title":"Traditional vs SDN","leftTitle":"Traditional","leftItems":["Na'urori masu zaman kansu","Hard to manage","Mafi aminci"],"rightTitle":"SDN","rightItems":["Software-based","Flexible","Mai sauƙin sarrafa"]},{"type":"hub","title":"SDN","center":"SDN","satellites":["Control","Data","Management","Application"]},{"type":"stack","title":"SDN Stack","items":["Applications","Controller","Infrastructure","Devices"]}
"@

Insert-SuppDiag $f 'net-a5' @"
{"type":"flow","title":"5G","items":["Shigar da 5G","Haɗa da Network","Saita Device","Yi Amfani"],"caption":"5G yana ba da sauri da ƙarfi fiye da 4G"},{"type":"compare","title":"4G vs 5G","leftTitle":"4G","leftItems":["Sauri ƙanƙani","Mafi yawan coverage","Mafi araha"],"rightTitle":"5G","rightItems":["Sauri mai girma","Ƙanƙanin coverage","Mafi tsada"]},{"type":"hub","title":"5G","center":"5G","satellites":["eMBB","URLLC","mMTC","Network Slicing"]},{"type":"stack","title":"5G Stack","items":["Core Network","Radio Access","Device","Application"]}
"@

Insert-SuppDiag $f 'net-a6' @"
{"type":"flow","title":"Sana'o'in Networking","items":["Zaɓin Sana'a","Koyon Fasaha","Certification","Neman Aiki"],"caption":"Sana'o'in networking suna da buƙata masu yawa"},{"type":"compare","title":"Admin vs Engineer","leftTitle":"Network Admin","leftItems":["Kula da network","Troubleshooting","Mafi aikace-aikace"],"rightTitle":"Network Engineer","rightItems":["Tsara network","Gina infrastructure","Mafi ƙarfi"]},{"type":"hub","title":"Networking Careers","center":"Network","satellites":["Admin","Engineer","Security","Architect"]},{"type":"stack","title":"Career Stack","items":["Architecture","Engineering","Security","Administration"]}
"@

Write-Output "Networking course done"

# ============================================================
# PROGRAMMING - 18 lessons
# ============================================================
$f = "$targetDir\programming.json"

Insert-SuppDiag $f 'prog-b1' @"
{"type":"flow","title":"Shirye-shirye","items":["Rubuta Code","Kwamfuta Ta Karɓi","Ta Aiwatar","Ta Nuna Sakamako"],"caption":"Programing shine hanyar yi wa kwamfuta"},{"type":"compare","title":"Low-level vs High-level","leftTitle":"Low-level","leftItems":["Machine code","Mai hankali","Mafi ƙarfi"],"rightTitle":"High-level","rightItems":["Python, JS","Mai sauƙi","Mafi yawan amfani"]},{"type":"hub","title":"Programming","center":"Code","satellites":["Variables","Functions","Loops","Data"]},{"type":"stack","title":"Programming Stack","items":["Runtime","Compiler","Language","Hardware"]}
"@

Insert-SuppDiag $f 'prog-b2' @"
{"type":"flow","title":"Variables","items":["Zaɓin Suna","Yi Rubutu","Yi Amfani","Yi Canzawa"],"caption":"Variables suna adana bayanai don amfani da su"},{"type":"compare","title":"String vs Number vs Boolean","leftTitle":"String","leftItems":["Rubutu","\"Sannu\"","Mai rikici"],"rightTitle":"Number","rightItems":["Lambobi","25, 3.14","Mai sauƙi"]},{"type":"hub","title":"Data Types","center":"Type","satellites":["String","Integer","Float","Boolean"]},{"type":"stack","title":"Type Stack","items":["Complex","Reference","Primitive","Null"]}
"@

Insert-SuppDiag $f 'prog-b3' @"
{"type":"flow","title":"If/Else","items":["Yi Tambaya","Duba Sharadi","Yi Abin da ya dace","Kammala"],"caption":"If/Else yana ba ka damar yanke shawara"},{"type":"compare","title":"If vs Switch","leftTitle":"If/Else","leftItems":["Mafi kyau ga sharadi","Mafi sauƙi","Mafi yawan amfani"],"rightTitle":"Switch","rightItems":["Mafi kyau ga zabin","Mai tsari","Mafi zurfin gwaji"]},{"type":"hub","title":"Conditionals","center":"If","satellites":["if","else if","else","switch"]},{"type":"stack","title":"Conditional Stack","items":["Pattern Matching","Switch","else if","if"]}
"@

Insert-SuppDiag $f 'prog-b4' @"
{"type":"flow","title":"Loops","items":["Fara Loop","Yi Aiki","Duba Sharadi","Kammala"],"caption":"Loops yana ba ka damar sake yin aiki"},{"type":"compare","title":"For vs While","leftTitle":"For Loop","leftItems":["Adadin sau da aka sani","Mafi sauƙi","Mafi yawan amfani"],"rightTitle":"While Loop","rightItems":["Har sai sharadi ya zama karya","Mafi kyau ga ma'lumot","Mafi rikici"]},{"type":"hub","title":"Loops","center":"Loop","satellites":["for","while","do-while","for-each"]},{"type":"stack","title":"Loop Stack","items":["Iteration","Condition","Body","Counter"]}
"@

Insert-SuppDiag $f 'prog-b5' @"
{"type":"flow","title":"Functions","items":["Kirkiri Function","Rubuta Body","Kira ta","Sake Amfani"],"caption":"Functions yana ba ka damar sake amfani da code"},{"type":"compare","title":"Function vs Method","leftTitle":"Function","leftItems":["Mai zaman kansa","Ba ta da class","Mai sauƙi"],"rightTitle":"Method","rightItems":["Yana cikin class","Yana amfani da object","Mafi rikici"]},{"type":"hub","title":"Functions","center":"Function","satellites":["Parameters","Return","Scope","Recursion"]},{"type":"stack","title":"Function Stack","items":["Call Stack","Parameters","Body","Return"]}
"@

Insert-SuppDiag $f 'prog-b6' @"
{"type":"flow","title":"Zaben Yare","items":["Gano Manufa","Zaɓi Yare","Koyi Fasaha","Far da Rubutu"],"caption":"Zaben yare yana dogara da manufar shirye-shirye"},{"type":"compare","title":"Python vs JavaScript","leftTitle":"Python","leftItems":["Mafi sauƙi","Data Science","AI/ML"],"rightTitle":"JavaScript","rightItems":["Web Development","Frontend + Backend","Mafi yawan amfani"]},{"type":"hub","title":"Languages","center":"Yare","satellites":["Python","JavaScript","Java","C++"]},{"type":"stack","title":"Language Stack","items":["Application","Framework","Language","Runtime"]}
"@

Insert-SuppDiag $f 'prog-i1' @"
{"type":"flow","title":"Arrays/Lists","items":["Kirkiri Array","Ƙara Element","Dubawa","Yi Amfani"],"caption":"Arrays yana adana jerin abubuwa"},{"type":"compare","title":"Array vs List","leftTitle":"Array","leftItems":["Girma ɗaya","Mafi sauri","Mafi aminci"],"rightTitle":"List","rightItems":["Girma mai canzawa","Mai sauƙi","Mafi yawan amfani"]},{"type":"hub","title":"Data Structures","center":"DS","satellites":["Array","List","Stack","Queue"]},{"type":"stack","title":"DS Stack","items":["Trees","Graphs","Lists","Arrays"]}
"@

Insert-SuppDiag $f 'prog-i2' @"
{"type":"flow","title":"OOP","items":["Gina Class","Kirkiri Object","Yi Methods","Haɗa Objects"],"caption":"OOP yana rarraba code zuwa kayan aiki"},{"type":"compare","title":"Class vs Object","leftTitle":"Class","leftItems":["Tsari/Shirye-shirye","Ba a iya amfani da shi kai tsaye","Blueprint"],"rightTitle":"Object","rightItems":["Instance na class","Ana amfani da shi","Abin da aka gina"]},{"type":"hub","title":"OOP","center":"OOP","satellites":["Encapsulation","Inheritance","Polymorphism","Abstraction"]},{"type":"stack","title":"OOP Stack","items":["Design Patterns","Classes","Objects","Interfaces"]}
"@

Insert-SuppDiag $f 'prog-i3' @"
{"type":"flow","title":"Git","items":["Yi Changes","git commit","git push","GitHub Ya Adana"],"caption":"Git yana ajiye tarihin canje-canjen code"},{"type":"compare","title":"Git vs GitHub","leftTitle":"Git","leftItems":["Local","Version control","Ba ta da web interface"],"rightTitle":"GitHub","rightItems":["Cloud","Yana haɗa da Git","Mafi yawan amfani"]},{"type":"hub","title":"Version Control","center":"VCS","satellites":["Git","GitHub","GitLab","Bitbucket"]},{"type":"stack","title":"Git Stack","items":["Remote","Local","Staging","Working Directory"]}
"@

Insert-SuppDiag $f 'prog-i4' @"
{"type":"flow","title":"Debugging","items":["Gano Bug","Duba Logs","Gyara Code","Sake Gwada"],"caption":"Debugging yana taimaka wajen gano matsalolin code"},{"type":"compare","title":"Print vs Debugger","leftTitle":"Print Statement","leftItems":["Mai sauƙi","Mai ban sha'awa","Ƙanƙanin"],"rightTitle":"Debugger","rightItems":["Mafi ƙarfi","Mai amfani","Mafi zurfin gwaji"]},{"type":"hub","title":"Debugging","center":"Debug","satellites":["Breakpoints","Logging","Profiling","Stack Traces"]},{"type":"stack","title":"Debug Stack","items":["Fix","Testing","Debugging","Detection"]}
"@

Insert-SuppDiag $f 'prog-i5' @"
{"type":"flow","title":"APIs","items":["Yi Buƙata","Tura zuwa Server","Karba Amsa","Yi Amfani"],"caption":"APIs yana haɗa shirye-shirye daban-daban"},{"type":"compare","title":"REST vs GraphQL","leftTitle":"REST","leftItems":["Endpoints da yawa","Mafi sauƙi","Mafi yawan amfani"],"rightTitle":"GraphQL","rightItems":["Endpoint ɗaya","Mafi kyau","Mafi inganci"]},{"type":"hub","title":"APIs","center":"API","satellites":["REST","GraphQL","gRPC","WebSocket"]},{"type":"stack","title":"API Stack","items":["Response","Request","Server","Client"]}
"@

Insert-SuppDiag $f 'prog-i6' @"
{"type":"flow","title":"Algorithms","items":["Fahimtar Matsala","Tsara Hanya","Rubuta Code","Gwada Performance"],"caption":"Algorithms suna da muhimmanci wajen warware matsaloli"},{"type":"compare","title":"Bubble Sort vs Quick Sort","leftTitle":"Bubble Sort","leftItems":["Mai sauƙi","O(n²)","Mai hankali"],"rightTitle":"Quick Sort","rightItems":["Mai rikici","O(n log n)","Mai sauri"]},{"type":"hub","title":"Algorithms","center":"Algorithm","satellites":["Sorting","Searching","Graph","Dynamic"]},{"type":"stack","title":"Algorithm Stack","items":["Complexity","Data Structures","Paradigms","Analysis"]}
"@

Insert-SuppDiag $f 'prog-a1' @"
{"type":"flow","title":"Trees & Graphs","items":["Gina Tree","Ƙara Nodes","Yi Traversal","Gano Path"],"caption":"Trees da Graphs suna da amfani a wasu matsaloli"},{"type":"compare","title":"Tree vs Graph","leftTitle":"Tree","leftItems":["ierarchical","ɗaya root","Mafi sauƙi"],"rightTitle":"Graph","rightItems":["Cyclical","Root da yawa","Mafi rikici"]},{"type":"hub","title":"Trees & Graphs","center":"Data","satellites":["Binary Tree","BST","Graph","Heap"]},{"type":"stack","title":"Tree Stack","items":["Traversal","Balancing","Insertion","Search"]}
"@

Insert-SuppDiag $f 'prog-a2' @"
{"type":"flow","title":"Design Patterns","items":["Gano Matsala","Zaɓin Pattern","Yi Amfani","Gwada"],"caption":"Design patterns suna ba da hanyoyin gyara matsaloli"},{"type":"compare","title":"Singleton vs Factory","leftTitle":"Singleton","leftItems":["Instance ɗaya","Mai sauƙi","Mafi yawan amfani"],"rightTitle":"Factory","rightItems":["Ƙirar objects","Mafi kyau","Mafi rikici"]},{"type":"hub","title":"Patterns","center":"Pattern","satellites":["Singleton","Factory","Observer","Strategy"]},{"type":"stack","title":"Pattern Stack","items":["Creational","Structural","Behavioral","Architectural"]}
"@

Insert-SuppDiag $f 'prog-a3' @"
{"type":"flow","title":"Testing","items":["Rubuta Test","Gwadi Code","Gano Kuskure","Gyara Code"],"caption":"Testing yana tabbatar da ingancin code"},{"type":"compare","title":"Unit vs Integration vs E2E","leftTitle":"Unit Test","leftItems":["Aiki ɗaya","Mai sauri","Mafi sauƙi"],"rightTitle":"E2E Test","rightItems":["Duk App","Mai hankali","Mafi zurfin gwaji"]},{"type":"hub","title":"Testing","center":"Test","satellites":["Unit","Integration","E2E","Performance"]},{"type":"stack","title":"Test Stack","items":["E2E","Integration","Unit","Linting"]}
"@

Insert-SuppDiag $f 'prog-a4' @"
{"type":"flow","title":"Concurrency","items":["Kirkiri Thread","Yi Aiki Daidai","Haɗa Sakamako","Sarrafa Matsaloli"],"caption":"Concurrency yana ba ka damar yin ayyuka da yawa"},{"type":"compare","title":"Thread vs Process","leftTitle":"Thread","leftItems":["Yana cikin process","Mai sauƙi","Mafi sauƙin sarrafa"],"rightTitle":"Process","rightItems":["Mai zaman kansa","Mai aminci","Mafi rikici"]},{"type":"hub","title":"Concurrency","center":"Concurrent","satellites":["Threads","Processes","Async","Parallel"]},{"type":"stack","title":"Concurrency Stack","items":["Synchronization","Communication","Scheduling","Parallelism"]}
"@

Insert-SuppDiag $f 'prog-a5' @"
{"type":"flow","title":"Secure Coding","items":["Gano Hadari","Yi Input Validation","Yi Sanitization","Yi Testing"],"caption":"Secure coding yana kare app daga barazana"},{"type":"compare","title":"Secure vs Insecure","leftTitle":"Insecure","leftItems":["Babu validation","SQL Injection","XSS"],"rightTitle":"Secure","rightItems":["Input validation","Parameterized queries","Output encoding"]},{"type":"hub","title":"Security","center":"Secure","satellites":["Input Validation","Authentication","Authorization","Encryption"]},{"type":"stack","title":"Secure Stack","items":["Runtime","Application","Network","Data"]}
"@

Insert-SuppDiag $f 'prog-a6' @"
{"type":"flow","title":"Sana'o'in Software","items":["Zaɓin Sana'a","Koyon Fasaha","Gina Portfolio","Neman Aiki"],"caption":"Sana'o'in software development suna da buƙata masu yawa"},{"type":"compare","title":"Frontend vs Backend","leftTitle":"Frontend","leftItems":["UI/UX","HTML/CSS/JS","Mai ganuwa"],"rightTitle":"Backend","rightItems":["Server","Database","Ba a ganuwa"]},{"type":"hub","title":"Software Careers","center":"Software","satellites":["Frontend","Backend","Full-Stack","DevOps"]},{"type":"stack","title":"Career Stack","items":["DevOps","Full-Stack","Backend","Frontend"]}
"@

Write-Output "Programming course done"

# ============================================================
# WEB DEVELOPMENT - 18 lessons
# ============================================================
$f = "$targetDir\web_development.json"

Insert-SuppDiag $f 'web-b1' @"
{"type":"flow","title":"Yadda Web Ke Aiki","items":["Ka Rubuta URL","Browser Ya Karɓi","Server Ya Amsa","Shafi Ya Nuna"],"caption":"Web yana aiki ta hanyar sauyon bayanai"},{"type":"compare","title":"Browser vs Server","leftTitle":"Browser","leftItems":["Yana karɓi HTML","Yana nuna shafi","Client side"],"rightTitle":"Server","rightItems":["Yana aika bayanai","Yana adana shafi","Server side"]},{"type":"hub","title":"Web","center":"Web","satellites":["Browser","Server","Database","CDN"]},{"type":"stack","title":"Web Stack","items":["Frontend","Backend","Database","Infrastructure"]}
"@

Insert-SuppDiag $f 'web-b2' @"
{"type":"flow","title":"Yadda Web Ke Aiki","items":["Browser Ya Karɓi Request","Server Ya Aika Response","Browser Ya Fassara","Ya Nuna Shafi"],"caption":"Browser yana fassara HTML don nuna shafi"},{"type":"compare","title":"Client vs Server","leftTitle":"Client","leftItems":["Browser","Yana karɓi","Yana nuna"],"rightTitle":"Server","rightItems":["Server","Yana aika","Yana adana"]},{"type":"hub","title":"Web Architecture","center":"Web","satellites":["Client","Server","Database","CDN"]},{"type":"stack","title":"Web Stack","items":["Infrastructure","Server","Application","Client"]}
"@

Insert-SuppDiag $f 'web-b3' @"
{"type":"flow","title":"HTML","items":["Kirkiri File","Rubuta Tags","Yi Structure","Yi Test"],"caption":"HTML shi ne gishirin shafin yanar gizo"},{"type":"compare","title":"HTML4 vs HTML5","leftTitle":"HTML4","leftItems":["Babu Audio/Video","Babu Canvas","Mafi yawan amfani"],"rightTitle":"HTML5","rightItems":["Audio/Video","Canvas","Sabon fasali"]},{"type":"hub","title":"HTML Tags","center":"HTML","satellites":["<h1>","<p>","<img>","<a>"]},{"type":"stack","title":"HTML Stack","items":["Semantic HTML","Forms","Media","Structure"]}
"@

Insert-SuppDiag $f 'web-b4' @"
{"type":"flow","title":"Yadda Yanar Gizo Ke","items":["Browser Ya Karɓi","HTML Ya Fassara","CSS Ya Gyara","JS Ya Rayar da"],"caption":"Shafin yanar gizo yana amfani da technologies da yawa"},{"type":"compare","title":"Static vs Dynamic","leftTitle":"Static","leftItems":["Ba ya canzawa","HTML kawai","Mai sauƙi"],"rightTitle":"Dynamic","rightItems":["Yana canzawa","JS + Backend","Mai rikici"]},{"type":"hub","title":"Web Tech","center":"Tech","satellites":["HTML","CSS","JavaScript","APIs"]},{"type":"stack","title":"Web Stack","items":["JavaScript","CSS","HTML","Browser"]}
"@

Insert-SuppDiag $f 'web-b5' @"
{"type":"flow","title":"CSS","items":["Zaɓin Element","Yi Style","Yi Layout","Yi Responsive"],"caption":"CSS yana ba shafin ka yadda zai bayyana"},{"type":"compare","title":"Inline vs Internal vs External","leftTitle":"Inline","leftItems":["A cikin tag","Mai sauƙi","Ƙanƙanin amfani"],"rightTitle":"External","rightItems":["File ɗaya","Mai kyau","Mafi yawan amfani"]},{"type":"hub","title":"CSS","center":"CSS","satellites":["Selectors","Properties","Values","Rules"]},{"type":"stack","title":"CSS Stack","items":["Framework","Preprocessor","Methodology","Vanilla"]}
"@

Insert-SuppDiag $f 'web-b6' @"
{"type":"flow","title":"Shafi na Farko","items":["Kirkiri index.html","Rubuta HTML","Ƙara CSS","Ƙara JavaScript"],"caption":"Shafi na farko yana nuna yadda HTML ke aiki"},{"type":"compare","title":"HTML vs CSS vs JS","leftTitle":"HTML","leftItems":["Tsari","Tags","Structure"],"rightTitle":"CSS","rightItems":["Launi","Layout","Style"]},{"type":"hub","title":"First Page","center":"Page","satellites":["HTML","CSS","JavaScript","Images"]},{"type":"stack","title":"Page Stack","items":["JavaScript","CSS","HTML","File"]}
"@

Insert-SuppDiag $f 'web-i1' @"
{"type":"flow","title":"Responsive Design","items":["Gano Viewport","Yi Media Queries","Yi Flexible Layout","Yi Test"],"caption":"Responsive design yana sa shafi ya dace da kowace na'ura"},{"type":"compare","title":"Desktop vs Mobile","leftTitle":"Desktop","leftItems":["Babban allo","Menu a gefe","Sarari mai yawa"],"rightTitle":"Mobile","rightItems":["Ƙanƙanin allo","Hamburger menu","Ƙanƙanin sarari"]},{"type":"hub","title":"Responsive","center":"Responsive","satellites":["Media Queries","Flexbox","Grid","Mobile First"]},{"type":"stack","title":"Responsive Stack","items":["Testing","Frameworks","Media Queries","Viewport"]}
"@

Insert-SuppDiag $f 'web-i2' @"
{"type":"flow","title":"Frontend Frameworks","items":["Zaɓin Framework","Koyi Fasaha","Gina App","Yi Deploy"],"caption":"Frameworks yana sa gina web apps ya sauƙa"},{"type":"compare","title":"React vs Vue vs Angular","leftTitle":"React","leftItems":["Meta","Mafi yawan amfani","Mai sauƙi"],"rightTitle":"Vue","rightItems":["Community","Mafi sauƙin koyo","Mai sauƙi"]},{"type":"hub","title":"Frameworks","center":"Framework","satellites":["React","Vue","Angular","Svelte"]},{"type":"stack","title":"Framework Stack","items":["Build Tools","State Management","Router","UI Library"]}
"@

Insert-SuppDiag $f 'web-i3' @"
{"type":"flow","title":"Backend","items":["Zaɓin Language","Gina API","Haɗa Database","Yi Deploy"],"caption":"Backend yana sarrafa bayanai da ayyuka na baya"},{"type":"compare","title":"Node.js vs Python vs PHP","leftTitle":"Node.js","leftItems":["JavaScript","Mai sauri","Mafi yawan amfani"],"rightTitle":"Python","rightItems":["Django/Flask","Mafi sauƙi","Mafi kyau ga data"]},{"type":"hub","title":"Backend","center":"Backend","satellites":["Node.js","Python","PHP","Java"]},{"type":"stack","title":"Backend Stack","items":["Framework","Language","Runtime","Server"]}
"@

Insert-SuppDiag $f 'web-i4' @"
{"type":"flow","title":"Databases","items":["Zaɓin DB","Yi Schema","Loda Bayanai","Haɗa da App"],"caption":"Databases yana adana bayananku a aminci"},{"type":"compare","title":"SQL vs NoSQL","leftTitle":"SQL","leftItems":["Teburin","Tsari mai tsayayye","ACID"],"rightTitle":"NoSQL","rightItems":["Document/KV","Tsari mai sassauci","Scalability"]},{"type":"hub","title":"Databases","center":"DB","satellites":["MySQL","PostgreSQL","MongoDB","Redis"]},{"type":"stack","title":"DB Stack","items":["Cache","Application","Database","Storage"]}
"@

Insert-SuppDiag $f 'web-i5' @"
{"type":"flow","title":"REST APIs","items":["Kirkiri Endpoint","Yi Request","Yi Response","Yi Validation"],"caption":"REST APIs yana haɗa frontend da backend"},{"type":"compare","title":"REST vs GraphQL","leftTitle":"REST","leftItems":["Endpoints da yawa","Mafi sauƙi","Mafi yawan amfani"],"rightTitle":"GraphQL","rightItems":["Endpoint ɗaya","Mafi kyau","Mafi inganci"]},{"type":"hub","title":"APIs","center":"API","satellites":["GET","POST","PUT","DELETE"]},{"type":"stack","title":"API Stack","items":["Documentation","Testing","Security","Implementation"]}
"@

Insert-SuppDiag $f 'web-i6' @"
{"type":"flow","title":"Hosting/Deployment","items":["Zaɓi Provider","Yi Deploy","Saitan Domain","Yi Monitor"],"caption":"Hosting yana sa shafinka ya zama accessible a web"},{"type":"compare","title":"Netlify vs Vercel vs AWS","leftTitle":"Netlify","leftItems":["Mai sauƙi","Kyauta ga ƙanƙani","Mai sauri"],"rightTitle":"Vercel","rightItems":["Mai kyau ga Next.js","Mai sauƙi","Mai ƙarfi"]},{"type":"hub","title":"Hosting","center":"Host","satellites":["Netlify","Vercel","AWS","GitHub Pages"]},{"type":"stack","title":"Hosting Stack","items":["CDN","Serverless","Server","Infrastructure"]}
"@

Insert-SuppDiag $f 'web-a1' @"
{"type":"flow","title":"Full-Stack","items":["Koyi Frontend","Koyi Backend","Haɗa da Database","Yi Deploy"],"caption":"Full-stack yana nufin iya yin komai"},{"type":"compare","title":"Frontend vs Backend vs Full-Stack","leftTitle":"Frontend","leftItems":["UI/UX","HTML/CSS/JS","Mai ganuwa"],"rightTitle":"Backend","rightItems":["Server","Database","Ba a ganuwa"]},{"type":"hub","title":"Full-Stack","center":"Full-Stack","satellites":["Frontend","Backend","DevOps","Database"]},{"type":"stack","title":"Full-Stack","items":["DevOps","Backend","Frontend","Database"]}
"@

Insert-SuppDiag $f 'web-a2' @"
{"type":"flow","title":"Performance","items":["Gano Matsalar","Yi Caching","Yi Lazy Loading","Yi Minification"],"caption":"Performance yana sa shafinka ya gudu da sauri"},{"type":"compare","title":"Before vs After Optimization","leftTitle":"Kafin","leftItems":["Sakan 6","Babu caching","Mai hankali"],"rightTitle":"Bayan","rightItems":["Sakan 2","Caching","Mai sauri"]},{"type":"hub","title":"Performance","center":"Perf","satellites":["Caching","CDN","Compression","Lazy Loading"]},{"type":"stack","title":"Perf Stack","items":["Monitoring","Optimization","Caching","CDN"]}
"@

Insert-SuppDiag $f 'web-a3' @"
{"type":"flow","title":"Web Security","items":["Gano Hadari","Yi Input Validation","Yi Output Encoding","Yi Testing"],"caption":"Web security yana kare shafinka daga barazana"},{"type":"compare","title":"XSS vs CSRF","leftTitle":"XSS","leftItems":["Injection","Client side","Mafi yawan amfani"],"rightTitle":"CSRF","rightItems":["Forgery","Server side","Mafi ban tsoro"]},{"type":"hub","title":"Web Security","center":"Security","satellites":["XSS","CSRF","SQL Injection","Authentication"]},{"type":"stack","title":"Security Stack","items":["WAF","HTTPS","Input Validation","Authentication"]}
"@

Insert-SuppDiag $f 'web-a4' @"
{"type":"flow","title":"PWA","items":["Shirya App Manifest","Yi Service Worker","Yi Offline Support","Yi Install"],"caption":"PWA yana ba ka damar samun app experience a web"},{"type":"compare","title":"PWA vs Native","leftTitle":"PWA","leftItems":["Mai sauƙi","Ba a buƙatar buga","Mafi yawan覆盖"],"rightTitle":"Native","rightItems":["Mafi ƙarfi","Mafi aminci","Mafi amfani"]},{"type":"hub","title":"PWA","center":"PWA","satellites":["Service Worker","Manifest","Cache","Push"]},{"type":"stack","title":"PWA Stack","items":["Push Notifications","Offline","Install","HTTPS"]}
"@

Insert-SuppDiag $f 'web-a5' @"
{"type":"flow","title":"SSR vs CSR","items":["Zaɓin Hanya","Shirya Code","Yi Render","Nuna ga Mai Amfani"],"caption":"SSR da CSR suna da bambanci wajen nuna shafi"},{"type":"compare","title":"SSR vs CSR","leftTitle":"SSR","leftItems":["Server yana gina","Mafi sauri","Mafi kyau ga SEO"],"rightTitle":"CSR","rightItems":["Browser yana gina","Mai rikici","Mafi kyau ga apps"]},{"type":"hub","title":"Rendering","center":"Render","satellites":["SSR","CSR","SSG","ISR"]},{"type":"stack","title":"Rendering Stack","items":["ISR","SSG","SSR","CSR"]}
"@

Insert-SuppDiag $f 'web-a6' @"
{"type":"flow","title":"Sana'o'in Web Dev","items":["Zaɓin Sana'a","Koyon Fasaha","Gina Portfolio","Neman Aiki"],"caption":"Sana'o'in web development suna da buƙata masu yawa"},{"type":"compare","title":"Frontend vs Backend vs UI/UX","leftTitle":"Frontend","leftItems":["HTML/CSS/JS","Browser","Mai ganuwa"],"rightTitle":"Backend","rightItems":["Server","Database","Ba a ganuwa"]},{"type":"hub","title":"Web Careers","center":"Web","satellites":["Frontend","Backend","Full-Stack","UI/UX"]},{"type":"stack","title":"Career Stack","items":["UI/UX","Full-Stack","Backend","Frontend"]}
"@

Write-Output "Web Development course done"
Write-Output "=== ALL DONE ==="
