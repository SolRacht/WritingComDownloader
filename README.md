# Save the interactives!

This program scrapes writing.com interactive stories. You can:

- Save stories to a local database
- Read them in your browser

You need a paid (basic or above) writing.com account to use this. It cannot handle the "Interactive Stories are Temporarily Unavailable" error page. 

```diff
! Be careful! It's possible to get IP-banned by writing.com when using this.
```

## Setup

To build and run this program, you need [Docker](https://www.docker.com/) and [Sqlite3](https://sqlite.org/index.html).

### Configuration

Before building, you need to change some values in `src/main/resources/applicaton.conf`:

- `my_session` and `user_ntoken`: These are your writing.com cookies.
  - If writing.com logs you out, you will need to log in again and update `application.conf` with your new cookies
- `use_config_stories`: this must be either `true` or `false`
  - `false`: save interactive stories you have favorited. These appear here: `writing.com/main/my_favorites`
  - `true`: only save the stories in `stories`. See the instructions in `application.conf`
- `slow_mode`: active by default in order to avoid putting too much load on the website. Hopefully avoids getting IP-banned, but this is not guaranteed. Set to `false` to scrape as fast as possible.

### Database setup

Before running, you need to create the database. The program saves stories to a local Sqlite database. This is just a file on your computer.
You will need to create it by following these steps (linux/mac command line):

```
# Create an empty file
touch data.db
# Run the migrations (assumes you are in in the base directory of this repo)
sqlite3 data.db < db/migration.sql
```

### Docker image build

Just run:

```
DOCKER_BUILDKIT=1 docker compose build
```

Re-run this every time the code updates, or if you update the configuration file.

## Usage

### Save the stories 

This will scrape writing.com for stories and save them to your local database.

Run the docker compose service `scrape`, supplying the path to the database you created earlier as an environment variable:

```
DB=/path/to/data.db docker compose run scrape
```

### Rendering HTML

This will render your stories into HTML that you can read in your browser. Start at `outline.html`. 
Note that this is a _very_ basic generator. It writes semantic HTML with no CSS styling.

Run the docker compose service `render`, supplying the path to your database and the directory in which stories will be written.
Supply the same directory over multiple runs: if new chapters are saved, the renderer will add them to the existing story and re-render preceding chapters so that the links work.

```
DB=/path/to/wcomdownloader.db RENDER=/path/to/directory/ docker compose run render
```
