-- Create users table
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'USER',
    is_email_verified BOOLEAN NOT NULL DEFAULT FALSE,
    is_account_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_login_at TIMESTAMP
);

-- Create transactions table
CREATE TABLE transactions (
    id BIGSERIAL PRIMARY KEY,
    transaction_name VARCHAR(255) NOT NULL,
    transaction_date TIMESTAMP NOT NULL,
    amount DECIMAL(15,2) NOT NULL CHECK (amount > 0),
    type VARCHAR(20) NOT NULL CHECK (type IN ('SENT', 'RECEIVED')),
    description TEXT,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create budgets table
CREATE TABLE budgets (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    type VARCHAR(20) NOT NULL CHECK (type IN ('AUTOMATED', 'MANUAL')),
    amount DECIMAL(15,2) NOT NULL CHECK (amount > 0),
    spent_amount DECIMAL(15,2) NOT NULL DEFAULT 0 CHECK (spent_amount >= 0),
    category VARCHAR(100) NOT NULL,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create subscriptions table
CREATE TABLE subscriptions (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    due_date DATE NOT NULL,
    amount DECIMAL(15,2) NOT NULL CHECK (amount > 0),
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE' CHECK (status IN ('ACTIVE', 'PENDING', 'CANCELLED', 'EXPIRED')),
    category VARCHAR(100) NOT NULL,
    auto_renew BOOLEAN NOT NULL DEFAULT FALSE,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create savings table
CREATE TABLE savings (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    saving_amount DECIMAL(15,2) NOT NULL CHECK (saving_amount > 0),
    date_taken DATE NOT NULL,
    amount_left DECIMAL(15,2) NOT NULL CHECK (amount_left >= 0),
    current_amount DECIMAL(15,2) NOT NULL DEFAULT 0 CHECK (current_amount >= 0),
    target_date DATE,
    description TEXT,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create loans table
CREATE TABLE loans (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    total_amount DECIMAL(15,2) NOT NULL CHECK (total_amount > 0),
    paid_amount DECIMAL(15,2) NOT NULL DEFAULT 0 CHECK (paid_amount >= 0),
    monthly_payment DECIMAL(15,2) NOT NULL CHECK (monthly_payment > 0),
    due_date DATE NOT NULL,
    interest_rate DECIMAL(5,2) NOT NULL CHECK (interest_rate >= 0),
    lender VARCHAR(255) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE' CHECK (status IN ('ACTIVE', 'PAID_OFF', 'DEFAULTED')),
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create reports table
CREATE TABLE reports (
    id BIGSERIAL PRIMARY KEY,
    report_month DATE NOT NULL,
    income DECIMAL(15,2) NOT NULL CHECK (income >= 0),
    expenses DECIMAL(15,2) NOT NULL CHECK (expenses >= 0),
    net_income DECIMAL(15,2) GENERATED ALWAYS AS (income - expenses) STORED,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, report_month)
);

-- Create indexes for better performance
CREATE INDEX idx_transactions_user_date ON transactions(user_id, transaction_date DESC);
CREATE INDEX idx_transactions_user_type ON transactions(user_id, type);
CREATE INDEX idx_budgets_user_category ON budgets(user_id, category);
CREATE INDEX idx_subscriptions_user_status ON subscriptions(user_id, status);
CREATE INDEX idx_subscriptions_user_due_date ON subscriptions(user_id, due_date);
CREATE INDEX idx_savings_user_created ON savings(user_id, created_at DESC);
CREATE INDEX idx_loans_user_status ON loans(user_id, status);
CREATE INDEX idx_loans_user_due_date ON loans(user_id, due_date);
CREATE INDEX idx_reports_user_month ON reports(user_id, report_month DESC);
