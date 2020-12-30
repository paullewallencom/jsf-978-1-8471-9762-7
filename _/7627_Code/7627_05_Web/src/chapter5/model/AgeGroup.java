package chapter5.model;

public enum AgeGroup {

	UNDER_TWENTY {
		@Override
		public int[] getRange() {
			return new int[] { 0, 20 };
		}

		@Override
		public String getLabel() {
			return "Under Twenty";
		}
	},
	TWENTY_TO_THIRTY {
		@Override
		public int[] getRange() {
			return new int[] { 20, 30 };
		}

		@Override
		public String getLabel() {
			return "Twenty to Thirty";
		}
	},

	THIRTY_TO_FORTY {
		@Override
		public int[] getRange() {
			return new int[] { 30, 40 };
		}

		@Override
		public String getLabel() {
			return "Thirty to Forty";
		}
	},
	FORTY_TO_FIFTY {
		@Override
		public int[] getRange() {
			return new int[] { 40, 50 };
		}

		@Override
		public String getLabel() {
			return "Forty to Fifty";
		}

	},
	FIFTY_TO_SIXTY {
		@Override
		public int[] getRange() {
			return new int[] { 50, 60 };
		}

		@Override
		public String getLabel() {
			return "Fifty to Sixty";
		}
	},
	OVER_SIXTY {
		@Override
		public int[] getRange() {
			return new int[] { 60, 120 };
		}

		@Override
		public String getLabel() {
			return "Over Sixty";
		}
	};

	public abstract int[] getRange();

	public abstract String getLabel();

	public String getNumericLabel() {
		int[] range = getRange();
		return range[0] + "-" + range[1];

	}
}
