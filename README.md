<h1 align="center">
  ANIFLIX
</h1>
<p align="center">
    <img src="https://img.shields.io/github/package-json/v/Jordi-Jaspers/aniflix?filename=frontend%2Fpackage.json" alt="GitHub package.json version (subfolder of monorepo)">
    <img src="https://img.shields.io/github/license/Jordi-Jaspers/aniflix" alt="License" >
    <img src="https://img.shields.io/github/commit-activity/m/Jordi-Jaspers/aniflix" alt="Commit Activity" >
    <img src="https://img.shields.io/github/last-commit/Jordi-Jaspers/aniflix" alt="Last Commit" >
</p>

---

## Table of Contents

- [Introduction](#introduction)
- [Learning Goals](#learning-goals)
- [Getting Started](#getting-started)
- [Stack](#stack)
- [References](#references)

---

## 📝 Introduction

**Author:** Jordi
Jaspers [[Github](https://github.com/Jordi-Jaspers "Github Page"), [Linkedin](https://www.linkedin.com/in/jordi-jaspers/ "Linkedin Page")]
<p align="left">
      <a href="https://ie.linkedin.com/in/jordi-jaspers">
         <img alt="Mail" title="Connect via email" src="https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor=white"/></a>
      <a href="https://ie.linkedin.com/in/jordi-jaspers">
         <img alt="LinkedIn" title="Connect on LinkedIn" src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white"/></a>
      <a href="https://github.com/Jordi-Jaspers?tab=followers">
         <img alt="followers" title="Follow me on Github" src="https://custom-icon-badges.demolab.com/github/followers/Jordi-Jaspers?color=236ad3&labelColor=1155ba&style=for-the-badge&logo=person-add&label=Follow&logoColor=white"/></a>
      <a href="https://github.com/Jordi-Jaspers?tab=repositories&sort=stargazers">
         <img alt="total stars" title="Total stars on GitHub" src="https://custom-icon-badges.demolab.com/github/stars/Jordi-Jaspers?color=55960c&style=for-the-badge&labelColor=488207&logo=star"/></a>
   </p>

**Description:**

> This is a small project to clone the Netflix UI with anime content instead of regular movies and series. The goal of this project is to learn more about Next.js and Tailwind CSS. Aswell as how to handle API calls in a frontend application. The API used for this project is the unofficial [GoGoAnime API V1](https://github.com/riimuru/gogoanime-api), there is also a newly updated [GoGoAnime API V2](https://github.com/consumet/api.consumet.org).

**Preview:**
Still need to share a link to the deployment

## 📝 Learning Goals <a name = "learning-goals"></a>

- [x] Learn Next.js
- [x] Learn TailwindCSS
- [x] Learn how to use Github Actions
- [x] Learn how to handle API Calls in a frontend application
- [x] Learn how to use the GoGoAnime API
- [x] Learn how to use the GoGoAnime API V2
- [x] Learn how to deploy a Next.js application to Vercel
- [x] ...

## 🏁 Getting Started <a name = "getting-started"></a>

add information here please.

collors:     // #1a1920
// #1E1E25

pocketbase stuff 

Another great PocketBase feature is how easy and simple it is to backup and restore.

To backup your application, it is usually enough to just copy the pb_data directory (for transactional safety make sure that the application is not running). Or alternatively, you could also use the sqlite3 .backup command on the database file.

To restore, as you may have already guessed, you just have to do the opposite - upload the pb_data backup directory to your server and restart the application.

email shit 

By default, PocketBase uses the internal Unix sendmail command for sending emails.
While it's OK for development, it's not very useful for production, because your emails most likely will get marked as spam or even fail to deliver.

To avoid deliverability issues, consider using a local SMTP server or an external mail service like MailerSend, Sendinblue, SendGrid, Mailgun, AWS SES, etc.

The ranges of Unicode characters which are routinely used for Chinese and Japanese text are:

U+3040 - U+30FF: hiragana and katakana (Japanese only)
U+3400 - U+4DBF: CJK unified ideographs extension A (Chinese, Japanese, and Korean)
U+4E00 - U+9FFF: CJK unified ideographs (Chinese, Japanese, and Korean)
U+F900 - U+FAFF: CJK compatibility ideographs (Chinese, Japanese, and Korean)
U+FF66 - U+FF9F: half-width katakana (Japanese only)

to check if a character contains a japanese/roman character you can use the following regex:
`/[一-龠]+|[ぁ-ゔ]+|[ァ-ヴー]+|[a-zA-Z0-9]+|[ａ-ｚＡ-Ｚ０-９]+|[々〆〤ヶ]+/u`

```tsx
const hls = new Hls({
    xhrSetup: xhr => {
        xhr.setRequestHeader("Referer", referer);
    }
})
```

```bash
docker pull redis:latest
docker run -v /myredis/conf:/usr/local/etc/redis -p 6379:6379 --name aniflix-redis redis:latest redis-server /usr/local/etc/redis/redis.conf

docker pull riimuru/consumet-api:latest
docker run -p 3001:3000 --name aniflix-consumet-api -d riimuru/consumet-api:latest

```

docker shite 

```dotenv
#frontend
NEXT_PUBLIC_CONSUMET_BASE_URL=...

# Consumet Api
CONSUMET_REDIS_HOST=...
CONSUMET_REDIS_PORT=...
```

frontend shite 

```dotenv
#frontend
NEXT_PUBLIC_CONSUMET_BASE_URL=...
NEXT_PUBLIC_POCKETBASE_URL=...
```

https://dev.to/thakkaryash94/build-nextjs-application-using-github-workflow-and-docker-3foj

Configure the following env variables

| ENV VARIABLE                  | DESCRIPTION                              |
|-------------------------------|------------------------------------------|
| NEXT_PUBLIC_CONSUMET_BASE_URL | The base url for the consumet Anime API. |
| NEXT_PUBLIC_CONSUMET_API_KEY  | The API key for the consumet Anime API.  |

https://www.admfactory.com/how-to-automatically-backup-files-and-directories-in-linux/


to use env variable at runtime or at client side instead of just server side you can use next.config.js or prefix env variable with NEXT_PUBLIC_*. more details....

https://developer.chrome.com/blog/autoplay/ ==> chrome autoplay policy


## 🛠️ Stack <a name = "stack"></a>

- [Spring Boot](https://spring.io/projects/spring-boot) - Java framework for building back-end applications.
- [Next.js](https://nextjs.org/) - React framework for building client-side applications.
- [Liquibase](https://www.liquibase.org/) - Database migration tool
- [PostgreSQL](https://www.postgresql.org/) - Open source object-relational database system.
- [Git](https://git-scm.com/) - Version Control

## 🚀 References <a name = "references"></a>

* Troubleshooting: <https://stackoverflow.com/>
* Next.js: <https://nextjs.org/docs/getting-started>
* TailwindCSS: <https://tailwindcss.com/docs>

https://www.youtube.com/watch?v=ABbww4CFQSo&ab_channel=developedbyed

https://docs.consumet.org/rest-api/Meta/anilist-anime/get-anime-info

https://consumet.aniflix.stream/meta/anilist/info/162803?provider=gogoanime
https://www.npmjs.com/package/svelte-motion
https://svelte-french-toast.com/
https://www.npmjs.com/package/svelte-player
https://lucide.dev/
https://www.shadcn-svelte.com/docs/components/dropdown-menu
https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html#application-properties.cache.spring.cache.redis.cache-null-values
https://github.com/SandroHc/reactive-jikan
https://docs.api.jikan.moe/#tag/anime/operation/getAnimeRecommendations
