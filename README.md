# Tastee
Recipies finder

## High-Level Architecture

1. Java + Spring backend application with REST API
2. SQL Database
3. In-memory cache for faster responses

## Functional Requirements

1. Search API - ingredients as input, recipients as output
2. Add API - adding new recipies
3. Get API - getting recipies by ID
4. Subscription API - getting information on new recipies matching given ingredients in the system to mailbox

## Observability & Monitoring

Tech: Micrometer, Prometheus & Grafana

Metrics:

1. REST API calls (latency, bytes, status codes)
2. Database Metrics (threads utilization, latencies, concurrency, bytes, IOPS)
3. Infrastucture - CPU, RAM, IOPS, Network, GC, Uptime, LoadAvg
4. Business objects - Subscriptions, Recipies
5. Cache metrics - hits, misses, evictions