package posl.engine.api;

import posl.engine.error.PoslException;

public interface StatementProvider {

	Object accept(StatementProviderVisitor visitor) throws PoslException;
}
