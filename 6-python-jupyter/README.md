# Python + Jupyter Lab

Four notebooks in one image:

- `notebook_analysis.ipynb` — tiny pandas/matplotlib data analysis on `data/sales.csv`.
- `notebook_ml_classification.ipynb` — scikit-learn classifier on the `digits` dataset (handwritten digit recognition).
- `notebook_sentiment.ipynb` — HuggingFace transformers sentiment analysis with a pre-baked DistilBERT model.
- `notebook_semantic_search.ipynb` — sentence-transformers embeddings + cosine similarity (the foundation of RAG).

Setting this up on your laptop is the classic Python PITA: wrong Python version,
broken `pip install`, native build failures on `numpy`/`pandas`/`scikit-learn`,
torch wheels that come bundled with 600 MB of CUDA you don't want, conflicts
with another project's virtualenv... Containerising it pins everything once and
for all — and lets you bake the ML model weights into the image so it works
offline.

## TASK 1: Containerize this notebook environment

Make a `Dockerfile` that:

1. Starts from a pinned Python base image (e.g. `python:3.12-slim`).
2. Installs the dependencies listed in `requirements.txt`.
3. Copies the notebook(s) and the `data/sales.csv` file into the image.
4. Exposes the Jupyter port and starts Jupyter Lab when the container runs.

Then build and run it:

```bash
docker build . --tag python-jupyter
docker run --rm -p 127.0.0.1:8888:8888 python-jupyter
```

Open <http://127.0.0.1:8888/lab?token=workshop> in your browser and run
`data_analysis.ipynb` end-to-end.

### First use a single stage, then split it into a build and runtime stage.

A multi-stage build can install packages into a `--user` site or a virtualenv in
the build stage, then copy only the site-packages into a slim runtime image.

## TASK 2: Edit the notebook from your host

Mount the folder into the container so changes saved in the browser appear on
your host (and vice versa):

```bash
docker run --rm -p 127.0.0.1:8888:8888 \
  -v "./notebooks:/app" \
  python-jupyter
```

Edit a notebook, save, and confirm the file on host disk has changed.

## TASK 3: Swap the dataset at runtime

Volume-mount your own CSV over `/data/sales.csv` and re-run the notebook
against it — no rebuild required.

## BONUS 1: Shrink the image

`python:3.12-slim` is already small, but pandas+numpy+torch+transformers pull
in a lot. Inspect the image size with `docker images` and see how small you
can get it (e.g. multi-stage, `--no-cache-dir`, CPU-only torch wheels,
removing build tools after install).

## BONUS 2: Bake the ML model weights into the image

The sentiment and semantic-search notebooks need model weights from HuggingFace.
By default they download on first run, which is slow and breaks if the
container has no internet. Pre-fetch them during `docker build` and set
`HF_HOME` so the notebooks find the cached weights — the image then starts
instantly and works fully offline.

This is not easy stuff, so look at the `Dockerfile.solution` if stuck.
