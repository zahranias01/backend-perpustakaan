const express = require('express');
const cors = require('cors');
const axios = require('axios');
const path = require('path');

const app = express();

app.use(cors());
app.use(express.json());

// === DEEPSEEK API KEY ===
const TOGETHER_API_KEY = 'dad00218e02a015fb296c27bc071637c87758cb2a0472f0cd6d4b2b8ed64a644';

// === ROUTE UNTUK MENERIMA CHAT ===
app.post('/chat', async (req, res) => {
  const userMessage = req.body.message;

  try {
    const messagesForAPI = [
      { role: "system", content: "Anda adalah asisten perpustakaan yang tahu semua buku, berpengetahuan luas, harus memakai Bahasa Indonesia (selalu pakai) dan juga jangan balas chat selain tentang buku, perpustakaan, rekomendasi buku dan yang tidak berbau buku." },
      { role: "user", content: userMessage }
    ];

    const response = await axios.post(
      'https://api.together.xyz/v1/chat/completions',
      {
        model: "deepseek-ai/DeepSeek-R1-Distill-Llama-70B-free", // atau model yang Anda gunakan
        messages: messagesForAPI, // Menggunakan array messages yang sudah ada instruksi sistem
        max_tokens: 512,
        temperature: 0.7
      },
      {
        headers: {
         'Authorization': `Bearer ${TOGETHER_API_KEY}`,
          'Content-Type': 'application/json',
        }
        
      }
    );

    // Untuk debugging: Cetak respons mentah dari Together AI
    console.log('Raw Together AI Response:', JSON.stringify(response.data, null, 2));

    let botReply = response.data.choices[0].message.content;

    // Membersihkan tag <think>...</think> dan whitespace di sekitarnya
    botReply = botReply.replace(/<think>[\s\S]*?<\/think>\s*/g, '').trim();

    res.json({ reply: botReply });
  } catch (error) {
    console.error('Together AI Error:', error.response?.data || error.message);
    // Cetak juga detail error jika ada
    if (error.response?.data) {
      console.error('Error Details:', JSON.stringify(error.response.data, null, 2));
    }
    res.status(500).json({ error: 'Gagal mendapatkan balasan dari Together AI' });
  }
});

// === SERVE FILE HTML CHATBOT ===
app.use(express.static(path.join(__dirname, 'chatbot-iu')));

app.get('/', (req, res) => {
  res.sendFile(path.join(__dirname, 'chatbot-iu', 'index.html'));
});

// === MENJALANKAN SERVER ===
app.listen(3000, () => {
  console.log('Server berjalan di http://localhost:3000');
});