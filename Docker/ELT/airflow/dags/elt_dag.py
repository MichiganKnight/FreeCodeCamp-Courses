from datetime import datetime, timedelta
from airflow import DAG
from docker.types import Mount
from airflow.operators.python_operators import PythonOperator
from airflow.operators.bash import BashOperator
from airflow.operators.docker import DockerOperator

import subprocess

default_args = {
    "owner": "airflow",
    "depends_on_past": False,
    "email_on_failure": False,
    "email_on_retry": False
}

def run_elt_scripts():
    script_path = "/opt/airflow/elt/elt_script.py"

    result = subprocess.run(
        ["python", script_path],
        capture_output=True,
        text=True
    )

    if result.returncode != 0:
        raise Exception(f"ELT Script Failed with Error: {result.stderr}")
    else:
        print(result.stdout)

dag = DAG(
    "elt_and_dbt",
    default_args=default_args,
    description="An ELT workflow with DBT",
    start_date=datetime(2025, 10, 28),
    catchup=False
)

t1 = PythonOperator(
    task_id="run_elt_scripts",
    python_callable=run_elt_scripts,
    dag=dag
)

t2 = DockerOperator(
    task_id="dbt_run",
    image="ghcr.io/dbt-labs/dbt-postgres:latest",
    command="dbt run --profiles-dir /root --project-dir /dbt",
    auto_remove=True,
    docker_url="unix://var/run/docker.sock",
    network_mode="bridge",
    mounts=[
        Mount(source="E:/FreeCodeCamp-Courses/Docker/ELT/airflow/custom_postgres", target="/dbt", type="bind"),
        Mount(source="C:/Users/flemi/.dbt", target="/root", type="bind")
    ],
    dag=dag
)

t1 >> t2