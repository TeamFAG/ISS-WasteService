FROM python:3.9

RUN useradd --create-home --shell /bin/bash app_user

WORKDIR /home/app_user
USER app_user
COPY truck.py .

CMD ["python", "truck.py"]